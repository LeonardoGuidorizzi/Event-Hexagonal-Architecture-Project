package dev.devdreamer.Event.application.usecases;

import dev.devdreamer.Event.application.port.in.CancelEventUseCase;
import dev.devdreamer.Event.application.port.out.EventRepository;
import dev.devdreamer.Event.domain.Event;
import dev.devdreamer.Event.domain.exception.EventNotFoundException;
import jakarta.transaction.Transactional;


@Transactional
public class CancelEventUseCaseImpl implements CancelEventUseCase {
    final EventRepository repository;

    public CancelEventUseCaseImpl(EventRepository repository) {
        this.repository = repository;
    }

    @Override
    public Event execute(String id) {
    Event event = repository.findEventById(id).orElseThrow(() -> new EventNotFoundException(id));
    event.cancel();
    return repository.save(event);
    }
}
