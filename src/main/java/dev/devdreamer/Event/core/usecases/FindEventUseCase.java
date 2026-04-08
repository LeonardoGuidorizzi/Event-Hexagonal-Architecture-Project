package dev.devdreamer.Event.core.gateway;

import dev.devdreamer.Event.core.domain.Event;

import java.util.List;

public interface FindEventUseCase {
    public List<Event> execute ();
}
