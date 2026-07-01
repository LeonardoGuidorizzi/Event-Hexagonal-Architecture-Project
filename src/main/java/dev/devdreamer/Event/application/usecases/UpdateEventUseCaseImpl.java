package dev.devdreamer.Event.application.usecases;

import dev.devdreamer.Event.core.domain.Event;
import dev.devdreamer.Event.core.port.out.EventRepository;

public class UpdateEventUseCaseImpl implements UpdateEventUseCase {
    private final EventRepository eventGateway;
    public UpdateEventUseCaseImpl(EventRepository eventGateway) {
        this.eventGateway = eventGateway;
    }

    @Override
    public Event execute(Long id, Event event) {

        return eventGateway.updateEvent(id, event);
    }
}
