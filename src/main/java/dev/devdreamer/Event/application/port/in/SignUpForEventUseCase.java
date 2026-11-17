package dev.devdreamer.Event.application.port.in;

import dev.devdreamer.Event.domain.Event;

public interface SignUpForEventUseCase {
    Event execute(String eventId, String userEmail);
}
