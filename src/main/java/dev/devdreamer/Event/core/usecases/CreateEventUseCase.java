package dev.devdreamer.Event.core.usecases;

import dev.devdreamer.Event.core.domain.Event;

public interface CreateEventUseCase {
    public Event execute (Event event);
}
