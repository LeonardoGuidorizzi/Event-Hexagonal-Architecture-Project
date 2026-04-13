package dev.devdreamer.Event.infrastructure.mapper;

import dev.devdreamer.Event.core.domain.Event;
import dev.devdreamer.Event.infrastructure.dto.EventDto;
import dev.devdreamer.Event.infrastructure.persistence.EventEntity;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

    // Domain → DTO  (para a camada de apresentação)
    public EventDto toDto(Event event) {
        return new EventDto(
                event.name(),
                event.description(),
                event.identifier(),
                event.local(),
                event.host(),
                event.capacity(),
                event.eventType(),
                event.start(),
                event.end()
        );
    }

    // DTO → Domain  (entrada vinda da apresentação; id e auditorias ficam nulos)
    public Event toDomain(EventDto dto) {
        return new Event(
                null,
                dto.name(),
                dto.description(),
                dto.identifier(),
                dto.local(),
                dto.host(),
                dto.capacity(),
                dto.eventType(),
                dto.start(),
                dto.end(),
                null,
                null
        );
    }

    // Entity → Domain  (saída vinda do banco; preserva id e auditorias)
    public Event toDomain(EventEntity entity) {
        return new Event(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getIdentifier(),
                entity.getLocal(),
                entity.getHost(),
                entity.getCapacity(),
                entity.getEventType(),
                entity.getStart(),
                entity.getEnd(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    // Domain → Entity  (para persistir; id nulo deixa o banco gerar)
    public EventEntity toEntity(Event event) {
        EventEntity entity = new EventEntity();
        entity.setName(event.name());
        entity.setDescription(event.description());
        entity.setIdentifier(event.identifier());
        entity.setLocal(event.local());
        entity.setHost(event.host());
        entity.setCapacity(event.capacity());
        entity.setEventType(event.eventType());
        entity.setStart(event.start());
        entity.setEnd(event.end());
        return entity;
    }
}

