package dev.devdreamer.Event.application.port.in;

import dev.devdreamer.Event.core.domain.Event;
import dev.devdreamer.Event.core.enums.EventStatus;
import dev.devdreamer.Event.core.enums.EventType;

import java.time.LocalDateTime;

public interface CreateEventUseCase {
    Event execute (CreateEventCommand command);
    record CreateEventCommand(
            String name,
            String description,
            String local,
            String host,
            Integer capacity,
            EventType eventType,
            LocalDateTime start,
            LocalDateTime end
    ){}
}
