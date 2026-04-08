package dev.devdreamer.Event.infrastructure.Mapper;

import dev.devdreamer.Event.core.domain.Event;
import dev.devdreamer.Event.infrastructure.dto.EventDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface EventMapper {
    Event toDomain (EventDto dto);
    EventDto toDto (Event domain);
}
