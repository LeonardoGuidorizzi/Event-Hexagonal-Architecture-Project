package dev.devdreamer.Event.application.port.out;

import dev.devdreamer.Event.core.domain.Event;

import java.util.List;

public interface EventRepository {
    Event save (Event event);

    List<Event> findAll ();

    List<Event> findEventById();

    List<Event> findEventByStatus(String name);

    void deleteById(Long id);

    Event updateEvent(Long id, Event event);
}
