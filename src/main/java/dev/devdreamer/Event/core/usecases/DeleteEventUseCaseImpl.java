package dev.devdreamer.Event.core.usecases;

import dev.devdreamer.Event.core.gateway.EventGateway;

public class DeleteEventUseCaseImpl implements DeleteEventUseCase{
    private final EventGateway eventGateway;

    public DeleteEventUseCaseImpl(EventGateway eventGateway) {
        this.eventGateway = eventGateway;
    }

    @Override
    public void execute(Long id) {
        eventGateway.deleteById(id);
    }
}
