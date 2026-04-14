package dev.devdreamer.Event.core.usecases;

import dev.devdreamer.Event.core.domain.Event;
import org.apache.catalina.User;

public interface UpdateEventUseCase {
    Event execute (Long id, Event event);
}
