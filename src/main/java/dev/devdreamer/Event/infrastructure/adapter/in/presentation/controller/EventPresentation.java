package dev.devdreamer.Event.infrastructure.adapter.in.presentation.controller;

import dev.devdreamer.Event.application.model.PageRequest;
import dev.devdreamer.Event.application.port.in.*;
import dev.devdreamer.Event.infrastructure.adapter.in.presentation.mapper.EventMapper;
import dev.devdreamer.Event.infrastructure.adapter.in.presentation.dto.CreateEventRequest;
import dev.devdreamer.Event.infrastructure.adapter.in.presentation.dto.EventResponse;
import dev.devdreamer.Event.infrastructure.adapter.in.presentation.dto.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.micrometer.core.annotation.Timed;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("api/v1/")
@Tag(name = "Events", description = "Create and manage events")
public class EventPresentation {

    private final CreateEventUseCase createEventUseCase;
    private final PublishEventUseCase publishEventUseCase;
    private final CancelEventUseCase cancelEventUseCase;
    private final DeleteEventUseCase deleteEventUseCase;
    private final UpdateEventUseCase updateEventUseCase;
    private final ListEventsUseCase listEventsUseCase;
    private final EventMapper mapper;

    public EventPresentation(CreateEventUseCase createEventUseCase, ListEventsUseCase listEventsUseCase, PublishEventUseCase publishEventUseCase, CancelEventUseCase cancelEventUseCase, DeleteEventUseCase deleteEventUseCase, UpdateEventUseCase updateEventUseCase, EventMapper mapper) {
        this.createEventUseCase = createEventUseCase;
        this.publishEventUseCase = publishEventUseCase;
        this.cancelEventUseCase = cancelEventUseCase;
        this.deleteEventUseCase = deleteEventUseCase;
        this.updateEventUseCase = updateEventUseCase;
        this.listEventsUseCase = listEventsUseCase;
        this.mapper = mapper;
    }

    @PostMapping("create")
    @Operation(summary = "Create an event", description = "Creates an event using the supplied details.")
    public ResponseEntity<EventResponse> createEvent (@Valid @RequestBody CreateEventRequest request){
        var command = mapper.toCommand(request);
        var event = createEventUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(event));
    }

    @GetMapping("getAll")
    @Operation(summary = "Find all events", description = "Shows all events")
    public ResponseEntity<PageResponse<EventResponse>> getAllEvents(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        var result = listEventsUseCase.execute(status, name, new PageRequest(page,size));
        var events = result.content().stream().map(mapper::toResponse).toList();
        var response = new PageResponse<>(
                events,
                result.page(),
                result.size(),
                result.total(),
                result.totalPages()
        );
        return ResponseEntity.ok(response);
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

    @DeleteMapping("{id}")
    @Operation(summary = "Delete an event", description = "Deletes the event identified by its ID.")
    public ResponseEntity<Void> deleteEvent(@PathVariable String id) {
        deleteEventUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    @Operation(summary = "Update an event", description = "Updates an event while it is in draft status.")
    public ResponseEntity<EventResponse> updateEvent(
            @PathVariable String id,
            @Valid @RequestBody CreateEventRequest request
    ) {
        var event = updateEventUseCase.execute(id, mapper.toUpdateCommand(request));
        return ResponseEntity.ok(mapper.toResponse(event));
    }


}
