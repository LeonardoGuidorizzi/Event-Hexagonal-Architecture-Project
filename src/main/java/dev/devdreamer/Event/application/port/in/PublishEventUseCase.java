package dev.devdreamer.Event.application.port.in;

import dev.devdreamer.Event.domain.Event;
import java.util.UUID;

public interface PublishEventUseCase {
    Event execute (String id, UUID userId);
}
