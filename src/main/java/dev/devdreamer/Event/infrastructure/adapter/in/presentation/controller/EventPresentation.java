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
    private final ListEventsUseCase listEventsUseCase;
    private final FindPublishedEventsUseCase findPublishedEventsUseCase;
    private final EventMapper mapper;

    public EventPresentation(CreateEventUseCase createEventUseCase, FindPublishedEventsUseCase findPublishedEventsUseCase, ListEventsUseCase listEventsUseCase, PublishEventUseCase publishEventUseCase, CancelEventUseCase cancelEventUseCase, EventMapper mapper ) {
        this.createEventUseCase = createEventUseCase;
        this.publishEventUseCase = publishEventUseCase;
        this.cancelEventUseCase = cancelEventUseCase;
        this.listEventsUseCase = listEventsUseCase;
        this.findPublishedEventsUseCase = findPublishedEventsUseCase;
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
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        var result = listEventsUseCase.execute(new PageRequest(page,size));
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

    @GetMapping("getPublishedEvents")
    @Operation(summary = "Find the published events", description = "Shows all available events")
    public ResponseEntity<PageResponse<EventResponse>> getPublishedEvents(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        var result = findPublishedEventsUseCase.execute(name,new PageRequest( page, size));
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


}
