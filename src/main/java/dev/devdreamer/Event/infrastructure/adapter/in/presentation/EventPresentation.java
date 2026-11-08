package dev.devdreamer.Event.infrastructure.adapter.in.presentation;

import dev.devdreamer.Event.application.port.in.CancelEventUseCase;
import dev.devdreamer.Event.application.port.in.CreateEventUseCase;
import dev.devdreamer.Event.application.port.in.PublishEventUseCase;
import dev.devdreamer.Event.infrastructure.adapter.in.presentation.Mapper.EventMapper;
import dev.devdreamer.Event.infrastructure.adapter.in.presentation.dto.CreateEventRequest;
import dev.devdreamer.Event.infrastructure.adapter.in.presentation.dto.EventResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.micrometer.core.annotation.Timed;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/v1/")
@Tag(name = "Events", description = "Create and manage events")
public class EventPresentation {

    private final CreateEventUseCase createEventUseCase;
    private final PublishEventUseCase publishEventUseCase;
    private final CancelEventUseCase cancelEventUseCase;
    private final EventMapper mapper;

    public EventPresentation(CreateEventUseCase createEventUseCase,PublishEventUseCase publishEventUseCase,  CancelEventUseCase cancelEventUseCase,  EventMapper mapper ) {
        this.createEventUseCase = createEventUseCase;
        this.publishEventUseCase = publishEventUseCase;
        this.cancelEventUseCase = cancelEventUseCase;
        this.mapper = mapper;
    }

    @PostMapping("create")
    @Operation(summary = "Create an event", description = "Creates an event using the supplied details.")
    public ResponseEntity<EventResponse> createEvent (@Valid @RequestBody CreateEventRequest request){
        var command = mapper.toCommand(request);
        var event = createEventUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(event));
    }
    @Timed(value = "PublishEventEndPoint", description = "it shows how long it takes to give the publish result")
    @PostMapping("publish/{id}")
    @Operation(summary = "Publish an event", description = "Publishes the event identified by its ID.")
    public ResponseEntity<EventResponse> publishEvent(@PathVariable String id){
        var event = publishEventUseCase.execute(id);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toResponse(event));
    }
    @Timed(value = "CancelEventEndPoint", description = "it shows how long it takes to give the cancel result")
    @PostMapping("cancel/{id}")
    @Operation(summary = "Cancel an event", description = "Cancels the event identified by its ID.")
    public ResponseEntity<EventResponse> cancelEvent(@PathVariable String id){
        var event = cancelEventUseCase.execute(id);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toResponse(event));
    }


}
