package dev.devdreamer.Event.domain;

import dev.devdreamer.Event.domain.enums.EventStatus;
import dev.devdreamer.Event.domain.enums.EventType;
import dev.devdreamer.Event.domain.exception.EventCapacityExceededException;
import dev.devdreamer.Event.domain.exception.InvalidEventStateException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {
    private static final LocalDateTime START = LocalDateTime.of(2030, 5, 1, 10, 0);
    private static final LocalDateTime END = START.plusHours(2);

    @Test
    void createBuildsDraftWithZeroRegistrationsAndGeneratedIdentifier() {
        Event event = newEvent();

        assertEquals("Community meetup", event.getName());
        assertEquals("A talk and discussion", event.getDescription());
        assertEquals("Main hall", event.getLocal());
        assertEquals("Alex", event.getHost());
        assertEquals(25, event.getCapacity());
        assertEquals(0, event.getRegisteredCount());
        assertEquals(EventType.WORKSHOP, event.getEventType());
        assertEquals(EventStatus.DRAFT, event.getStatus());
        assertEquals(START, event.getStart());
        assertEquals(END, event.getEnd());
        assertNull(event.getCancelledAt());
        assertTrue(event.getIdentifier().matches("\\d{5}"));
    }

    @Test
    void createRejectsMissingRequiredDetailsInvalidCapacityAndInvalidDates() {
        assertThrows(IllegalArgumentException.class, () -> Event.create(null, "Description", "Place", "Host", 1, START, END, EventType.MUSIC));
        assertThrows(IllegalArgumentException.class, () -> Event.create("Name", " ", "Place", "Host", 1, START, END, EventType.MUSIC));
        assertThrows(IllegalArgumentException.class, () -> Event.create("Name", "Description", "Place", "Host", 0, START, END, EventType.MUSIC));
        assertThrows(IllegalArgumentException.class, () -> Event.create("Name", "Description", "Place", "Host", 1, START, END, null));
        assertThrows(IllegalArgumentException.class, () -> Event.create("Name", "Description", "Place", "Host", 1, START, START, EventType.MUSIC));
    }

    @Test
    void publishChangesDraftStatusAndRejectsPublishingAgain() {
        Event event = newEvent();

        event.publish();

        assertEquals(EventStatus.PUBLISHED, event.getStatus());
        assertThrows(InvalidEventStateException.class, event::publish);
    }

    @Test
    void updateDetailsChangesDraftValues() {
        Event event = newEvent();
        LocalDateTime revisedStart = START.plusDays(1);
        LocalDateTime revisedEnd = revisedStart.plusHours(3);

        event.updateDetails("Updated", "New description", "New hall", "Sam", 30,
                EventType.SEMINAR, revisedStart, revisedEnd);

        assertEquals("Updated", event.getName());
        assertEquals("New description", event.getDescription());
        assertEquals("New hall", event.getLocal());
        assertEquals("Sam", event.getHost());
        assertEquals(30, event.getCapacity());
        assertEquals(EventType.SEMINAR, event.getEventType());
        assertEquals(revisedStart, event.getStart());
        assertEquals(revisedEnd, event.getEnd());
    }

    @Test
    void updateDetailsRejectsNonDraftAndCapacityBelowRegistrations() {
        Event published = newEvent();
        published.publish();
        assertThrows(InvalidEventStateException.class, () -> published.updateDetails(
                "Updated", "Description", "Place", "Host", 25, EventType.MUSIC, START, END));

        Event withRegistrations = newEvent();
        withRegistrations.setRegisteredCount(3);
        assertThrows(IllegalArgumentException.class, () -> withRegistrations.updateDetails(
                "Updated", "Description", "Place", "Host", 2, EventType.MUSIC, START, END));
    }

    @Test
    void cancelSetsCancelledStatusAndTimestampAndRejectsRepeatCancellation() {
        Event event = newEvent();

        event.cancel();

        assertEquals(EventStatus.CANCELLED, event.getStatus());
        assertNotNull(event.getCancelledAt());
        assertThrows(InvalidEventStateException.class, event::cancel);
    }

    @Test
    void signupUpdatesRegistrationCountAndAvailableSlots() {
        Event event = newEvent();
        event.publish();

        event.signUpParticipant(START.minusHours(1));

        assertEquals(1, event.getRegisteredCount());
        assertEquals(24, event.availableSlots());
        assertTrue(event.hasAvailableSlots());
    }

    @Test
    void signupRejectsUnpublishedPastAndFullEvents() {
        Event draft = newEvent();
        assertThrows(InvalidEventStateException.class, () -> draft.signUpParticipant(START.minusHours(1)));

        Event ended = newEvent();
        ended.publish();
        assertThrows(InvalidEventStateException.class, () -> ended.signUpParticipant(END));

        Event full = newEvent();
        full.publish();
        full.setRegisteredCount(full.getCapacity());
        assertFalse(full.hasAvailableSlots());
        assertEquals(0, full.availableSlots());
        assertThrows(EventCapacityExceededException.class, () -> full.signUpParticipant(START.minusHours(1)));
    }

    private Event newEvent() {
        return Event.create("Community meetup", "A talk and discussion", "Main hall", "Alex",
                25, START, END, EventType.WORKSHOP);
    }
}
