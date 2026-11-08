package dev.devdreamer.Event.application.usecases;

import dev.devdreamer.Event.application.port.in.PublishEventUseCase;
import dev.devdreamer.Event.application.port.out.EventRepository;
import dev.devdreamer.Event.domain.Event;
import dev.devdreamer.Event.domain.exception.EventNotFoundException;
import jakarta.transaction.Transactional;

@Transactional
public class PublishEventUseCaseImpl implements PublishEventUseCase {
    final EventRepository repository;
    public PublishEventUseCaseImpl(EventRepository repository) {
        this.repository = repository;
    }

    @Override
    public Event execute(String id) {
        Event event = repository.findEventById(id).orElseThrow(()-> new EventNotFoundException(id));
        event.publish();
        return repository.save(event);
    }
}
