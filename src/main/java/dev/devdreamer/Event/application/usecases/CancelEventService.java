package dev.devdreamer.Event.application.usecases;

import dev.devdreamer.Event.application.port.in.CancelEventUseCase;
import dev.devdreamer.Event.application.port.out.EventRepository;
import dev.devdreamer.Event.core.domain.Event;
import dev.devdreamer.Event.core.exception.EventNotFoundException;

import java.util.Optional;
import java.util.UUID;

public class CancelEventService implements CancelEventUseCase {
    final EventRepository repository;

    public CancelEventService(EventRepository repository) {
        this.repository = repository;
    }

    @Override
    public Event execute(String id) {
    Event event = repository.findEventById(id).orElseThrow(() -> new EventNotFoundException(id));
    event.cancel();
    return repository.save(event);
    }
}
