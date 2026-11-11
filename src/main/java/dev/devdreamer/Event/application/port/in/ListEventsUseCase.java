package dev.devdreamer.Event.application.port.in;

import dev.devdreamer.Event.application.model.PageRequest;
import dev.devdreamer.Event.application.model.PageResult;
import dev.devdreamer.Event.domain.Event;

import java.util.List;

public interface ListEventsUseCase {
    PageResult<Event> execute(String status, String name, PageRequest request);
}
