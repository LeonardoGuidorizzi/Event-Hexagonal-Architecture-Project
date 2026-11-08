package dev.devdreamer.Event.application.port.out;

import dev.devdreamer.Event.domain.Event;

import java.util.List;
import java.util.Optional;

public interface EventRepository {
    Event save (Event event);

    Event findEventByStatus(String status);

    Optional<Event> findEventById(String id);

    List<Event> findAll ();

    void deleteById(Long id);

    Event updateEvent(Long id, Event event);
}
