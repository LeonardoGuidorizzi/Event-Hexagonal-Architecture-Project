package dev.devdreamer.Event.presentation;

import dev.devdreamer.Event.core.domain.Event;
import dev.devdreamer.Event.core.usecases.CreateEventUseCase;
import dev.devdreamer.Event.core.usecases.FindEventUseCase;
import dev.devdreamer.Event.infrastructure.Mapper.EventMapper;
import dev.devdreamer.Event.infrastructure.dto.EventDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;  

@Controller
@RequestMapping("api/v1/")
public class EventPresentation {
    private final CreateEventUseCase createEventUseCase;
    private final FindEventUseCase findEventUseCase;
    private final EventMapper eventMapper;

    public EventPresentation(CreateEventUseCase createEventUseCase, FindEventUseCase findEventUseCase, EventMapper eventMapper) {
        this.createEventUseCase = createEventUseCase;
        this.findEventUseCase= findEventUseCase;
        this.eventMapper = eventMapper;
    }

    @PostMapping("/create")
    public EventDto createEvent (@RequestBody EventDto request){
        Event newEvent = createEventUseCase.execute(eventMapper.toDomain(request));
        return eventMapper.toDto(newEvent);
    }

}
