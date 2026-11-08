package dev.devdreamer.Event.application.port.in;

import dev.devdreamer.Event.domain.Event;

public interface PublishEventUseCase {
    Event execute (String id);
}
