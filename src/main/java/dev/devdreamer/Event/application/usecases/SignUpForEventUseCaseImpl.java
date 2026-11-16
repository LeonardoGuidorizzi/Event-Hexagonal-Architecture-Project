package dev.devdreamer.Event.application.usecases;

import dev.devdreamer.Event.application.port.in.SignUpForEventUseCase;
import dev.devdreamer.Event.application.port.out.EventParticipantRepository;
import dev.devdreamer.Event.application.port.out.EventRepository;
import dev.devdreamer.Event.application.port.out.UserRepository;
import dev.devdreamer.Event.domain.Event;
import dev.devdreamer.Event.domain.User;
import dev.devdreamer.Event.domain.exception.DuplicateEventSignUpException;
import dev.devdreamer.Event.domain.exception.EventNotFoundException;

import java.time.Clock;

public class SignUpForEventUseCaseImpl implements SignUpForEventUseCase {
    private final EventRepository events;
    private final UserRepository users;
    private final EventParticipantRepository participants;
    private final Clock clock;

    public SignUpForEventUseCaseImpl(EventRepository events, UserRepository users,
                                     EventParticipantRepository participants, Clock clock) {
        this.events = events;
        this.users = users;
        this.participants = participants;
        this.clock = clock;
    }

    @Override
    public Event execute(String eventId, String userEmail) {
        User user = users.findByEmail(User.normalizeEmail(userEmail))
                .orElseThrow(() -> new IllegalStateException("Authenticated user does not exist"));
        Event event = events.findEventByIdForUpdate(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        if (participants.exists(eventId, user.id())) throw new DuplicateEventSignUpException();
        event.signUpParticipant(java.time.LocalDateTime.now(clock));
        participants.save(eventId, user.id());
        return events.save(event);
    }
}
