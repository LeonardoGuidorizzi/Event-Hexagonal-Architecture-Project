package dev.devdreamer.Event.application.usecases;

import dev.devdreamer.Event.application.port.in.CreateEventUseCase;
import dev.devdreamer.Event.application.port.out.EventRepository;
import dev.devdreamer.Event.core.domain.Event;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CreateEventService implements CreateEventUseCase {
    private final EventRepository repository;

    public CreateEventService(EventRepository repository) {
        this.repository = repository;
    }

    @Override
    public Event execute(CreateEventCommand command) {
        Event event = Event.create(
                command.name(),
                command.description(),
                command.local(),
                command.host(),
                command.capacity(),
                command.start(),
                command.end(),
                command.eventType());
        return repository.save(event);
    }
}
