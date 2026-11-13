package dev.devdreamer.Event.application.usecases;

import dev.devdreamer.Event.application.port.in.UpdateEventUseCase;
import dev.devdreamer.Event.application.port.out.EventRepository;
import dev.devdreamer.Event.domain.Event;
import dev.devdreamer.Event.domain.exception.EventNotFoundException;
import jakarta.transaction.Transactional;

@Transactional
public class UpdateEventUseCaseImpl implements UpdateEventUseCase {
    private final EventRepository repository;

    public UpdateEventUseCaseImpl(EventRepository repository) {
        this.repository = repository;
    }

    @Override
    public Event execute(String id, UpdateEventCommand command) {
        Event event = repository.findEventById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
        event.updateDetails(
                command.name(),
                command.description(),
                command.local(),
                command.host(),
                command.capacity(),
                command.eventType(),
                command.start(),
                command.end()
        );
        return repository.save(event);
    }
}
