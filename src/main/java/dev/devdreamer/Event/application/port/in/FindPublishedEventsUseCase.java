package dev.devdreamer.Event.application.port.in;

import dev.devdreamer.Event.application.model.PageRequest;
import dev.devdreamer.Event.application.model.PageResult;
import dev.devdreamer.Event.domain.Event;

public interface FindPublishedEventsUseCase {
    PageResult<Event> execute(String name, PageRequest request);
}
