package dev.devdreamer.Event.application.usecases;

import dev.devdreamer.Event.core.domain.Event;

public interface CreateEventUseCase {
    public Event execute (Event event);
}
