package dev.devdreamer.Event.infrastructure.presentation;

import dev.devdreamer.Event.infrastructure.dto.EventDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/")
public class EventPresentation {
    public String createEvent (@RequestBody EventDto request){
        return "Event created";
    }
}
