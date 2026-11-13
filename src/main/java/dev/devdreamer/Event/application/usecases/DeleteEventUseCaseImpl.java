package dev.devdreamer.Event.application.usecases;

import dev.devdreamer.Event.application.port.in.DeleteEventUseCase;
import dev.devdreamer.Event.application.port.out.EventRepository;
import dev.devdreamer.Event.domain.exception.EventNotFoundException;
import jakarta.transaction.Transactional;

@Transactional
public class DeleteEventUseCaseImpl implements DeleteEventUseCase {
    private final EventRepository repository;

    public DeleteEventUseCaseImpl(EventRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(String id) {
        if (repository.findEventById(id).isEmpty()) {
            throw new EventNotFoundException(id);
        }
        repository.deleteById(id);
    }
}
