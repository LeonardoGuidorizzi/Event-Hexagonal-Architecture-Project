package dev.devdreamer.Event.presentation;

import dev.devdreamer.Event.core.domain.Event;
import dev.devdreamer.Event.core.usecases.CreateEventUseCase;
import dev.devdreamer.Event.core.usecases.FindEventUseCase;
import dev.devdreamer.Event.infrastructure.mapper.EventMapper;
import dev.devdreamer.Event.infrastructure.dto.EventDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("api/v1/")
public class EventPresentation {
    private final CreateEventUseCase createEventUseCase;
    private final FindEventUseCase findEventUseCase;
    private final EventMapper mapper;

    public EventPresentation(CreateEventUseCase createEventUseCase, FindEventUseCase findEventUseCase, EventMapper mapper) {
        this.createEventUseCase = createEventUseCase;
        this.findEventUseCase= findEventUseCase;
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


}
