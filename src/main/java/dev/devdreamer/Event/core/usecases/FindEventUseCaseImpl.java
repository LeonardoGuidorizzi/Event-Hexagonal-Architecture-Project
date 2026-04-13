package dev.devdreamer.Event.core.usecases;

import dev.devdreamer.Event.core.domain.Event;
import dev.devdreamer.Event.core.gateway.EventGateway;

import java.util.List;

public class FindEventUseCaseImpl implements FindEventUseCase {
    private final EventGateway eventGateway;
    public FindEventUseCaseImpl(EventGateway eventGateway) {
        this.eventGateway = eventGateway;
    }

    @Override
    public List<Event> execute() {
        return eventGateway.findEvent();
    }
}
