package dev.devdreamer.Event.core.gateway;

import dev.devdreamer.Event.core.domain.Event;

public interface EventGateway {
    public Event createEvent (Event event);
}
