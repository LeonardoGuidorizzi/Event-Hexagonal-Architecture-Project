package dev.devdreamer.Event.core.usecases;

import dev.devdreamer.Event.core.domain.Event;
import dev.devdreamer.Event.core.gateway.EventGateway;
import org.apache.catalina.User;

public class UpdateEventUseCaseImpl implements UpdateEventUseCase {
    private final EventGateway eventGateway;
    public UpdateEventUseCaseImpl(EventGateway eventGateway) {
        this.eventGateway = eventGateway;
    }

    @Override
    public Event execute(Long id, Event event) {

        return eventGateway.updateEvent(id, event);
    }
}
