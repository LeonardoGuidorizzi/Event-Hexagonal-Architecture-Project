package dev.devdreamer.Event.application.port.in;

import dev.devdreamer.Event.core.domain.Event;

public interface RegisterParticipantUseCase {
    public Event execute (String id);
}
