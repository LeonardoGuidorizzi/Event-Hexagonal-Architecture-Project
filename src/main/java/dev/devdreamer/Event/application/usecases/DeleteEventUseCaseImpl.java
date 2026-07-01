package dev.devdreamer.Event.application.usecases;

import dev.devdreamer.Event.core.port.out.EventRepository;

public class DeleteEventUseCaseImpl implements DeleteEventUseCase{
    private final EventRepository eventGateway;

    public DeleteEventUseCaseImpl(EventRepository eventGateway) {
        this.eventGateway = eventGateway;
    }

    @Override
    public void execute(Long id) {
        eventGateway.deleteById(id);
    }
}
