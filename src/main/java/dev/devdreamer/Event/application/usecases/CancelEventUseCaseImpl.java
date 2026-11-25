package dev.devdreamer.Event.application.usecases;

import dev.devdreamer.Event.application.port.in.CancelEventUseCase;
import dev.devdreamer.Event.application.port.out.EventRepository;
import dev.devdreamer.Event.domain.Event;
import dev.devdreamer.Event.domain.exception.EventNotFoundException;
import java.util.UUID;


public class CancelEventUseCaseImpl implements CancelEventUseCase {
    final EventRepository repository;

    public CancelEventUseCaseImpl(EventRepository repository) {
        this.repository = repository;
    }

    @Override
    public Event execute(String id, UUID userId) {
    Event event = repository.findEventById(id).orElseThrow(() -> new EventNotFoundException(id));
    event.assertCreator(userId);
    event.cancel();
    return repository.save(event);
    }
}
