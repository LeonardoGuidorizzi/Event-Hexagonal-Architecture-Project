package dev.devdreamer.Event.application.usecases;

import dev.devdreamer.Event.application.model.PageRequest;
import dev.devdreamer.Event.application.model.PageResult;
import dev.devdreamer.Event.application.port.in.*;
import dev.devdreamer.Event.application.port.out.EventParticipantRepository;
import dev.devdreamer.Event.application.port.out.EventRepository;
import dev.devdreamer.Event.application.port.out.UserRepository;
import dev.devdreamer.Event.domain.Event;
import dev.devdreamer.Event.domain.User;
import dev.devdreamer.Event.domain.enums.EventStatus;
import dev.devdreamer.Event.domain.enums.EventType;
import dev.devdreamer.Event.domain.exception.DuplicateEventSignUpException;
import dev.devdreamer.Event.domain.exception.EventCapacityExceededException;
import dev.devdreamer.Event.domain.exception.EventNotFoundException;
import dev.devdreamer.Event.domain.exception.InvalidEventStateException;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EventUseCaseTests {
    private static final LocalDateTime START = LocalDateTime.of(2030, 5, 1, 10, 0);
    private static final LocalDateTime END = START.plusHours(2);
    private static final Instant NOW = Instant.parse("2030-04-01T12:00:00Z");
    private static final Clock CLOCK = Clock.fixed(NOW, ZoneOffset.UTC);
    private static final UUID OWNER = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

    @Test
    void createEventPersistsNewDraft() {
        FakeEvents events = new FakeEvents();
        CreateEventUseCase useCase = new CreateEventUseCaseImpl(events);

        Event created = useCase.execute(createCommand());

        assertSame(created, events.saved.getFirst());
        assertEquals(EventStatus.DRAFT, created.getStatus());
        assertEquals(0, created.getRegisteredCount());
    }

    @Test
    void createEventDoesNotPersistInvalidCommand() {
        FakeEvents events = new FakeEvents();
        CreateEventUseCase useCase = new CreateEventUseCaseImpl(events);

        assertThrows(IllegalArgumentException.class, () -> useCase.execute(new CreateEventUseCase.CreateEventCommand(
                " ", "Description", "Place", "Host", 10, EventType.MUSIC, START, END, OWNER)));
        assertTrue(events.saved.isEmpty());
    }

    @Test
    void createEventRejectsZeroCapacityAndEndEqualToStartWithoutSaving() {
        FakeEvents events = new FakeEvents();
        CreateEventUseCase useCase = new CreateEventUseCaseImpl(events);

        assertThrows(IllegalArgumentException.class, () -> useCase.execute(new CreateEventUseCase.CreateEventCommand(
                "Meetup", "Description", "Place", "Host", 0, EventType.MUSIC, START, END, OWNER)));
        assertThrows(IllegalArgumentException.class, () -> useCase.execute(new CreateEventUseCase.CreateEventCommand(
                "Meetup", "Description", "Place", "Host", 10, EventType.MUSIC, START, START, OWNER)));
        assertTrue(events.saved.isEmpty());
    }

    @Test
    void publishAndCancelFindMutateAndPersistEvent() {
        FakeEvents events = new FakeEvents();
        Event draft = event();
        events.existing = draft;

        Event published = new PublishEventUseCaseImpl(events).execute("event-1", OWNER);
        assertSame(draft, published);
        assertEquals(EventStatus.PUBLISHED, published.getStatus());
        assertSame(published, events.saved.getFirst());

        Event cancelled = new CancelEventUseCaseImpl(events).execute("event-1", OWNER);
        assertSame(published, cancelled);
        assertEquals(EventStatus.CANCELLED, cancelled.getStatus());
        assertNotNull(cancelled.getCancelledAt());
        assertSame(cancelled, events.saved.getLast());
    }

    @Test
    void publishAndCancelPropagateMissingEventAndInvalidState() {
        FakeEvents missing = new FakeEvents();
        assertThrows(EventNotFoundException.class, () -> new PublishEventUseCaseImpl(missing).execute("missing", OWNER));
        assertThrows(EventNotFoundException.class, () -> new CancelEventUseCaseImpl(missing).execute("missing", OWNER));
        assertTrue(missing.saved.isEmpty());

        FakeEvents alreadyPublished = new FakeEvents();
        alreadyPublished.existing = event();
        alreadyPublished.existing.publish();
        assertThrows(InvalidEventStateException.class, () -> new PublishEventUseCaseImpl(alreadyPublished).execute("event-1", OWNER));
        assertTrue(alreadyPublished.saved.isEmpty());

        FakeEvents alreadyCancelled = new FakeEvents();
        alreadyCancelled.existing = event();
        alreadyCancelled.existing.cancel();
        assertThrows(InvalidEventStateException.class, () -> new CancelEventUseCaseImpl(alreadyCancelled).execute("event-1", OWNER));
        assertTrue(alreadyCancelled.saved.isEmpty());
    }

    @Test
    void updateEventMutatesAndPersistsExistingEvent() {
        FakeEvents events = new FakeEvents();
        events.existing = event();
        UpdateEventUseCase.UpdateEventCommand command = new UpdateEventUseCase.UpdateEventCommand(
                "Updated", "New description", "New place", "Sam", 15, EventType.SEMINAR, START.plusDays(1), END.plusDays(1));

        Event updated = new UpdateEventUseCaseImpl(events).execute("event-1", command, OWNER);

        assertSame(events.existing, updated);
        assertEquals("Updated", updated.getName());
        assertEquals(15, updated.getCapacity());
        assertEquals(EventType.SEMINAR, updated.getEventType());
        assertEquals(1, events.saved.size());
    }

    @Test
    void updateEventDoesNotSaveMissingOrInvalidUpdate() {
        FakeEvents missing = new FakeEvents();
        assertThrows(EventNotFoundException.class, () -> new UpdateEventUseCaseImpl(missing).execute("missing", updateCommand(), OWNER));
        assertTrue(missing.saved.isEmpty());

        FakeEvents published = new FakeEvents();
        published.existing = event();
        published.existing.publish();
        assertThrows(InvalidEventStateException.class, () -> new UpdateEventUseCaseImpl(published).execute("event-1", updateCommand(), OWNER));
        assertTrue(published.saved.isEmpty());
    }

    @Test
    void deleteEventSucceedsAndThrowsWhenEventDoesNotExist() {
        FakeEvents events = new FakeEvents();
        DeleteEventUseCase useCase = new DeleteEventUseCaseImpl(events);

        useCase.execute("event-1", OWNER);
        assertEquals(List.of("event-1"), events.deletedIds);

        events.deleteResult = false;
        assertThrows(EventNotFoundException.class, () -> useCase.execute("missing", OWNER));
        assertEquals(List.of("event-1", "missing"), events.deletedIds);
    }

    @Test
    void listEventsDelegatesFiltersAndPageRequestToRepository() {
        FakeEvents events = new FakeEvents();
        PageRequest request = new PageRequest(2, 20);
        PageResult<Event> expected = new PageResult<>(List.of(event()), 2, 20, 41, 3);
        events.pageResult = expected;

        PageResult<Event> actual = new ListEventsUseCaseImpl(events).execute("PUBLISHED", "meetup", request);

        assertSame(expected, actual);
        assertEquals("PUBLISHED", events.statusFilter);
        assertEquals("meetup", events.nameFilter);
        assertSame(request, events.pageRequest);
    }

    @Test
    void signUpForEventNormalizesUserEmailAndPersistsParticipantAndUpdatedEvent() {
        FakeEvents events = new FakeEvents();
        events.existing = event();
        events.existing.publish();
        FakeUsers users = new FakeUsers();
        users.existing = new User(UUID.randomUUID(), "person@example.com", "hash", null);
        FakeParticipants participants = new FakeParticipants();

        Event signedUp = new SignUpForEventUseCaseImpl(events, users, participants, CLOCK)
                .execute("event-1", " Person@Example.COM ");

        assertEquals(List.of("person@example.com"), users.lookups);
        assertEquals(1, signedUp.getRegisteredCount());
        assertEquals("event-1", participants.savedEventIds.getFirst());
        assertEquals(users.existing.id(), participants.savedUserIds.getFirst());
        assertEquals(1, events.saved.size());
    }

    @Test
    void signUpForEventRejectsMissingUserEventDuplicateAndUnpublishedEvent() {
        FakeEvents events = new FakeEvents();
        events.existing = event();
        events.existing.publish();
        FakeUsers users = new FakeUsers();
        FakeParticipants participants = new FakeParticipants();

        assertThrows(IllegalStateException.class, () -> new SignUpForEventUseCaseImpl(events, users, participants, CLOCK)
                .execute("event-1", "person@example.com"));
        assertTrue(participants.savedEventIds.isEmpty());

        users.existing = new User(UUID.randomUUID(), "person@example.com", "hash", null);
        FakeEvents missingEvent = new FakeEvents();
        assertThrows(EventNotFoundException.class, () -> new SignUpForEventUseCaseImpl(missingEvent, users, participants, CLOCK)
                .execute("missing", "person@example.com"));

        participants.alreadyExists = true;
        assertThrows(DuplicateEventSignUpException.class, () -> new SignUpForEventUseCaseImpl(events, users, participants, CLOCK)
                .execute("event-1", "person@example.com"));

        participants.alreadyExists = false;
        FakeEvents draftEvent = new FakeEvents();
        draftEvent.existing = event();
        assertThrows(InvalidEventStateException.class, () -> new SignUpForEventUseCaseImpl(draftEvent, users, participants, CLOCK)
                .execute("event-1", "person@example.com"));
        assertTrue(participants.savedEventIds.isEmpty());
    }

    @Test
    void signUpForEventRejectsFullEventWithoutSavingParticipantOrEvent() {
        FakeEvents events = new FakeEvents();
        events.existing = Event.create("Full", "Description", "Place", "Host", 1,
                START, END, EventType.MUSIC, OWNER);
        events.existing.publish();
        events.existing.setRegisteredCount(1);
        FakeUsers users = new FakeUsers();
        users.existing = new User(UUID.randomUUID(), "person@example.com", "hash", null);
        FakeParticipants participants = new FakeParticipants();

        assertThrows(EventCapacityExceededException.class,
                () -> new SignUpForEventUseCaseImpl(events, users, participants, CLOCK)
                        .execute("event-1", "person@example.com"));

        assertTrue(participants.savedEventIds.isEmpty());
        assertTrue(events.saved.isEmpty());
        assertEquals(1, events.existing.getRegisteredCount());
    }

    @Test
    void signUpForEventRejectsAtEventEndWithoutSavingParticipantOrEvent() {
        LocalDateTime now = LocalDateTime.of(2030, 4, 1, 12, 0);
        Clock clockAtEnd = Clock.fixed(now.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);
        FakeEvents events = new FakeEvents();
        events.existing = Event.create("Ending", "Description", "Place", "Host", 5,
                now.minusHours(2), now, EventType.MUSIC, OWNER);
        events.existing.publish();
        FakeUsers users = new FakeUsers();
        users.existing = new User(UUID.randomUUID(), "person@example.com", "hash", null);
        FakeParticipants participants = new FakeParticipants();

        assertThrows(InvalidEventStateException.class, () -> new SignUpForEventUseCaseImpl(events, users, participants, clockAtEnd)
                .execute("event-1", "person@example.com"));

        assertTrue(participants.savedEventIds.isEmpty());
        assertTrue(events.saved.isEmpty());
        assertEquals(0, events.existing.getRegisteredCount());
    }

    @Test
    void cleanupUsesRetentionCutoffAndReturnsDeletedCount() {
        FakeEvents events = new FakeEvents();
        events.deletedCancelledCount = 4;

        int deleted = new DeleteExpiredCancelledEventsImpl(events, CLOCK).execute();

        assertEquals(4, deleted);
        assertEquals(LocalDateTime.of(2030, 4, 1, 11, 58), events.cleanupCutoff);
    }

    private static CreateEventUseCase.CreateEventCommand createCommand() {
        return new CreateEventUseCase.CreateEventCommand("Meetup", "Description", "Place", "Host", 10,
                EventType.MUSIC, START, END, OWNER);
    }

    private static UpdateEventUseCase.UpdateEventCommand updateCommand() {
        return new UpdateEventUseCase.UpdateEventCommand("Updated", "Description", "Place", "Host", 10,
                EventType.MUSIC, START, END);
    }

    private static Event event() {
        return Event.create("Meetup", "Description", "Place", "Host", 10, START, END, EventType.MUSIC, OWNER);
    }

    private static final class FakeEvents implements EventRepository {
        private final List<Event> saved = new ArrayList<>();
        private final List<String> deletedIds = new ArrayList<>();
        private Event existing;
        private boolean deleteResult = true;
        private PageResult<Event> pageResult = new PageResult<>(List.of(), 0, 10, 0, 0);
        private String statusFilter;
        private String nameFilter;
        private PageRequest pageRequest;
        private LocalDateTime cleanupCutoff;
        private int deletedCancelledCount;

        @Override public Event save(Event event) { saved.add(event); return event; }
        @Override public Optional<Event> findEventById(String id) { return Optional.ofNullable(existing); }
        @Override public Optional<Event> findEventByIdForUpdate(String id) { return Optional.ofNullable(existing); }
        @Override public PageResult<Event> findAll(String status, String name, PageRequest request) {
            statusFilter = status;
            nameFilter = name;
            pageRequest = request;
            return pageResult;
        }
        @Override public boolean deleteById(String id) { deletedIds.add(id); return deleteResult; }
        @Override public int deleteCancelledBefore(LocalDateTime cutoff) { cleanupCutoff = cutoff; return deletedCancelledCount; }
    }

    private static final class FakeUsers implements UserRepository {
        private User existing;
        private final List<String> lookups = new ArrayList<>();
        @Override public Optional<User> findByEmail(String email) {
            lookups.add(email);
            if (existing != null && existing.email().equals(email)) return Optional.of(existing);
            return Optional.empty();
        }
        @Override public User save(User user) { existing = user; return user; }
    }

    private static final class FakeParticipants implements EventParticipantRepository {
        private boolean alreadyExists;
        private final List<String> savedEventIds = new ArrayList<>();
        private final List<UUID> savedUserIds = new ArrayList<>();
        @Override public boolean exists(String eventId, UUID userId) { return alreadyExists; }
        @Override public void save(String eventId, UUID userId) {
            savedEventIds.add(eventId);
            savedUserIds.add(userId);
        }
    }
}
