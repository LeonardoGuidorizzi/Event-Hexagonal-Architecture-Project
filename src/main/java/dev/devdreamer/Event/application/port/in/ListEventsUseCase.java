package dev.devdreamer.Event.application.port.in;

import dev.devdreamer.Event.domain.Event;

import java.util.List;

public interface ListEventsUseCase {
    List<Event> execute();
}
