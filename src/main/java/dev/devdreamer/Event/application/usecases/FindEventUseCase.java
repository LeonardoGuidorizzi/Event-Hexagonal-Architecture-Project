package dev.devdreamer.Event.application.usecases;

import dev.devdreamer.Event.core.domain.Event;

import java.util.List;

public interface FindEventUseCase {
    public List<Event> execute ();
}
