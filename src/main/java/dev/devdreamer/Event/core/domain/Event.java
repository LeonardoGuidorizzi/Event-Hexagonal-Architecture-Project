package dev.devdreamer.Event.core.domain;

import dev.devdreamer.Event.core.enums.EventType;

import java.time.LocalDateTime;

public record Event(
        String id,
        String name,
        String description,
        String identifier,
        String local,
        String host,
        String capacity,
        EventType eventType,
        LocalDateTime start,
        LocalDateTime end,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
