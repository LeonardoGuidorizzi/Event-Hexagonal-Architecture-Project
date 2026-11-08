package dev.devdreamer.Event.infrastructure.adapter.out.persistence.mapper;


import dev.devdreamer.Event.domain.Event;
import dev.devdreamer.Event.infrastructure.adapter.out.persistence.entity.EventEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventJpaMapper {
    EventEntity toEntity (Event event);
    Event toDomain (EventEntity entity);
}
