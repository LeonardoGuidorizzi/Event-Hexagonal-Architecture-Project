package dev.devdreamer.Event.infrastructure.adapter.in.presentation.dto;

import dev.devdreamer.Event.domain.enums.EventType;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record CreateEventRequest(
        @NotBlank(message = "O campo do nome evento é obrigatorio")
        @Size(max = 200, message = "O nome do evento não pode ser maior que 200 caracteres")
        String name,
        String description,
        @NotBlank(message = "O campo do local do evento é obrigatorio")
        String local,
        @NotBlank(message = "O campo de responsavel pelo evento é obrigatorio")
        @Size(max = 200, message = "O nome do responsavel não pode ser maior que 200 caracteres")
        String host,
        @NotNull(message = "O campo de capacidade do evento é obrigatorio")
        @Positive(message = "Capacidade deve ser positiva")
        Integer capacity,
        @NotNull(message = "O campo de Tipo do evento é obrigatorio")
        EventType eventType,
        @NotNull(message = "O campo de data de inicio do evento é obrigatorio")
        @Future(message = "Data de início deve ser no futuro")
        LocalDateTime start,
        @NotNull(message = "O campo de data de fim do evento é obrigatorio")
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