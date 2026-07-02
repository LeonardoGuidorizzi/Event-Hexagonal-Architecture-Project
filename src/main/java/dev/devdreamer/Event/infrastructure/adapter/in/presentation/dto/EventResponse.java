package dev.devdreamer.Event.infrastructure.adapter.in.presentation.dto;

import dev.devdreamer.Event.core.enums.EventStatus;
import dev.devdreamer.Event.core.enums.EventType;

import java.time.LocalDateTime;

public record EventResponse (
        String id,
        String name,
        String description,
        String identifier,
        String local,
        String host,
        Integer capacity,
        Integer registeredParticipantCapacity,
        EventType eventType,
        EventStatus status,
        LocalDateTime start,
        LocalDateTime end,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

){}


