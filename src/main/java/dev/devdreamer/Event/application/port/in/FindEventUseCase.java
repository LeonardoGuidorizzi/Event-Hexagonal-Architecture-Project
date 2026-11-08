package dev.devdreamer.Event.application.port.in;

import dev.devdreamer.Event.domain.Event;

import java.util.List;

public interface FindEventUseCase {
    Event findById(String id);
    List<Event> findAll();
    List<Event> findPublished(String name);
}
