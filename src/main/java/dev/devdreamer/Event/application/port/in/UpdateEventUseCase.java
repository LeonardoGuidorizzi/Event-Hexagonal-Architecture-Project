package dev.devdreamer.Event.application.port.in;

import dev.devdreamer.Event.domain.Event;
import dev.devdreamer.Event.domain.enums.EventType;

import java.time.LocalDateTime;
import java.util.UUID;

public interface UpdateEventUseCase {
    Event execute(String id, UpdateEventCommand command, UUID userId);

    record UpdateEventCommand(
            String name,
            String description,
            String local,
            String host,
            Integer capacity,
            EventType eventType,
            LocalDateTime start,
            LocalDateTime end
    ) {}
}
