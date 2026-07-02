package dev.devdreamer.Event.application.usecases;

import dev.devdreamer.Event.application.port.in.PublishEventUseCase;
import dev.devdreamer.Event.application.port.out.EventRepository;
import dev.devdreamer.Event.core.domain.Event;
import dev.devdreamer.Event.core.exception.EventNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PublishEventService implements PublishEventUseCase {
    final EventRepository repository;
    public PublishEventService(EventRepository repository) {
        this.repository = repository;
    }

    @Override
    public Event execute(String id) {
        Event event = repository.findEventById(id).orElseThrow(()-> new EventNotFoundException(id));
        event.publish();
        return repository.save(event);
    }
}
