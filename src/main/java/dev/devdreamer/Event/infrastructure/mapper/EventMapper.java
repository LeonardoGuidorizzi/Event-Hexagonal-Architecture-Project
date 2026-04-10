package dev.devdreamer.Event.core.mapper;

import dev.devdreamer.Event.core.domain.Event;
import dev.devdreamer.Event.infrastructure.dto.EventDto;
import dev.devdreamer.Event.infrastructure.persistence.EventEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface EventMapper {
    //---------------PRESENTATION------------------//
    Event toDomain (EventDto dto);
    EventDto toDto (Event domain);
    //---------------REPOSITORY GATEWAY------------------//
    EventEntity toEntity (Event Event);
    Event toDomain (EventEntity Entity);
}
