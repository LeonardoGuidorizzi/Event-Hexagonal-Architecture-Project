package dev.devdreamer.Event.application.usecases;

import dev.devdreamer.Event.core.domain.Event;
import dev.devdreamer.Event.core.port.out.EventRepository;
//i could add context using the @Service annotation here, but i'd be breaking the clean arch rule
//that's why i used a @Bean annotation in the infrastructure
public class CreateEventUseCaseImpl implements CreateEventUseCase{
    private final EventRepository eventGateway;

    public CreateEventUseCaseImpl(EventRepository eventGateway) {
        this.eventGateway = eventGateway;
    }

    @Override
    public Event execute(Event event) {
        return eventGateway.createEvent(event);
    }
}
