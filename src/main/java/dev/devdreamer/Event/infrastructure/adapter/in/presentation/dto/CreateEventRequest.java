package dev.devdreamer.Event.infrastructure.adapter.in.presentation.dto;

import dev.devdreamer.Event.domain.enums.EventType;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record CreateEventRequest(
        @NotBlank(message = "Event name is required")
        @Size(max = 200, message = "Event name must not exceed 200 characters")
        String name,
        @NotBlank(message = "Event description is required")
        String description,
        @NotBlank(message = "Event location is required")
        String local,
        @NotBlank(message = "Event host is required")
        @Size(max = 200, message = "Event host must not exceed 200 characters")
        String host,
        @NotNull(message = "Event capacity is required")
        @Positive(message = "Event capacity must be positive")
        Integer capacity,
        @NotNull(message = "Event type is required")
        EventType eventType,
        @NotNull(message = "Event start date is required")
        @Future(message = "Event start date must be in the future")
        LocalDateTime start,
        @NotNull(message = "Event end date is required")
        LocalDateTime end


){}
//String name,
//String description,
//String identifier,
//String local,
//String host,
//Integer capacity,
//Integer registeredParticipantCapacity,
//EventType eventType,
//LocalDateTime start,
//LocalDateTime end
