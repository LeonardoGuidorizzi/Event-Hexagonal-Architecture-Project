package dev.devdreamer.Event.infrastructure.dto;

import dev.devdreamer.Event.core.enums.EventType;

import java.time.LocalDateTime;

public record EventDto(
        String name,
        String description,
        String identifier,
        String local,
        String host,
        String capacity,
        EventType eventType,
        LocalDateTime start,
        LocalDateTime end
) {
}
