package dev.devdreamer.Event.application.port.in;

import dev.devdreamer.Event.domain.Event;

public interface UpdateEventUseCase {
    Event execute (Long id, Event event);
}
