package dev.devdreamer.Event.application.port.in;

import dev.devdreamer.Event.core.domain.Event;

public interface CancelEventUseCase {
   public Event execute (String id);
}
