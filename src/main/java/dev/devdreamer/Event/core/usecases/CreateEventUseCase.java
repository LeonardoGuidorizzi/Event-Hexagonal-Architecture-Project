package dev.devdreamer.Event.core.gateway;

import dev.devdreamer.Event.core.domain.Event;

public interface CreateEventUseCase {
    public Event execute (Event event);
}
