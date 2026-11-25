package dev.devdreamer.Event.application.usecases;

import dev.devdreamer.Event.application.port.in.DeleteEventUseCase;
import dev.devdreamer.Event.application.port.out.EventRepository;
import dev.devdreamer.Event.domain.exception.EventNotFoundException;
import dev.devdreamer.Event.domain.Event;
import java.util.UUID;

public class DeleteEventUseCaseImpl implements DeleteEventUseCase {
    private final EventRepository repository;

    public DeleteEventUseCaseImpl(EventRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(String id, UUID userId) {
        Event event = repository.findEventById(id).orElseThrow(() -> new EventNotFoundException(id));
        event.assertCreator(userId);
        if (!repository.deleteById(id)) {
            throw new EventNotFoundException(id);
        }
    }
}
