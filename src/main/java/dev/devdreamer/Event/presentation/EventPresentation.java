package dev.devdreamer.Event.presentation;

import dev.devdreamer.Event.core.domain.Event;
import dev.devdreamer.Event.core.usecases.*;
import dev.devdreamer.Event.infrastructure.mapper.EventMapper;
import dev.devdreamer.Event.infrastructure.dto.EventDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/v1/")
public class EventPresentation {

    private final CreateEventUseCase createEventUseCase;
    private final FindEventUseCase findEventUseCase;
    private final DeleteEventUseCase deleteEventUseCase;
    private final UpdateEventUseCase updateEventUseCase;
    private final EventMapper mapper;

    public EventPresentation(CreateEventUseCase createEventUseCase, FindEventUseCase findEventUseCase, DeleteEventUseCase deleteEventUseCase, UpdateEventUseCase updateEventUseCase, EventMapper mapper) {
        this.createEventUseCase = createEventUseCase;
        this.findEventUseCase= findEventUseCase;
        this.deleteEventUseCase = deleteEventUseCase;
        this.updateEventUseCase = updateEventUseCase;
        this.mapper = mapper;
    }

    @PostMapping("create")
    public EventDto createEvent (@RequestBody EventDto request){
        Event newEvent = createEventUseCase.execute(mapper.toDomain(request));
        return mapper.toDto(newEvent);
    }

    @GetMapping("getAll")
    public ResponseEntity<List<EventDto>> getAllEvent(){
        List<EventDto> events = findEventUseCase.execute()
                .stream()
                .map(mapper::toDto)
                .toList();
        return ResponseEntity.ok(events);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteEventById(@PathVariable Long id){
        deleteEventUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("update/{id}")
    public ResponseEntity<EventDto> updateEvent(@PathVariable Long id,@RequestBody EventDto request ){
        Event event = updateEventUseCase.execute(id, mapper.toDomain(request));
        return ResponseEntity.ok(mapper.toDto(event));
    }


}
