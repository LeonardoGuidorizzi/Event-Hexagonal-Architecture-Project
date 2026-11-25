package dev.devdreamer.Event.application.usecases;

import dev.devdreamer.Event.application.port.in.PublishEventUseCase;
import dev.devdreamer.Event.application.port.out.EventRepository;
import dev.devdreamer.Event.domain.Event;
import dev.devdreamer.Event.domain.exception.EventNotFoundException;
import java.util.UUID;

public class PublishEventUseCaseImpl implements PublishEventUseCase {
    final EventRepository repository;
    public PublishEventUseCaseImpl(EventRepository repository) {
        this.repository = repository;
    }

    @Override
    public Event execute(String id, UUID userId) {
        Event event = repository.findEventById(id).orElseThrow(()-> new EventNotFoundException(id));
        event.assertCreator(userId);
        event.publish();
        return repository.save(event);
    }
}
