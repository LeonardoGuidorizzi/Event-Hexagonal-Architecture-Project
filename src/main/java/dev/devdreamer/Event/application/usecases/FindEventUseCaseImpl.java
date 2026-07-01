package dev.devdreamer.Event.application.usecases;

import dev.devdreamer.Event.core.domain.Event;
import dev.devdreamer.Event.core.port.out.EventRepository;

import java.util.List;

public class FindEventUseCaseImpl implements FindEventUseCase {
    private final EventRepository eventGateway;
    public FindEventUseCaseImpl(EventRepository eventGateway) {
        this.eventGateway = eventGateway;
    }

    @Override
    public List<Event> execute() {
        return eventGateway.findEventById();
    }
}
