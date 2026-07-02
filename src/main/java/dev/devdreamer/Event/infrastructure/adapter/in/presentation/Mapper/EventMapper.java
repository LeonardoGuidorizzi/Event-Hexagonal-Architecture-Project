package dev.devdreamer.Event.infrastructure.adapter.in.presentation.Mapper;

import dev.devdreamer.Event.application.port.in.CreateEventUseCase.CreateEventCommand;
import dev.devdreamer.Event.core.domain.Event;
import dev.devdreamer.Event.infrastructure.adapter.in.presentation.dto.CreateEventRequest;
import dev.devdreamer.Event.infrastructure.adapter.in.presentation.dto.EventResponse;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {
    public CreateEventCommand toCommand (CreateEventRequest request){
        return new CreateEventCommand(
                request.name(),
                request.description(),
                request.local(),
                request.host(),
                request.capacity(),
                request.eventType(),
                request.start(),
                request.end()
        );
    }

    public EventResponse toResponse (Event event){
        return new EventResponse(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getIdentifier(),
                event.getLocal(),
                event.getHost(),
                event.getCapacity(),
                event.getRegisteredCount(),
                event.getEventType(),
                event.getStatus(),
                event.getStart(),
                event.getEnd(),
                event.getCreatedAt(),
                event.getUpdatedAt()
        );
    }

}

