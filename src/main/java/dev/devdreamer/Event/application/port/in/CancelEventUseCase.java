package dev.devdreamer.Event.application.port.in;

import dev.devdreamer.Event.domain.Event;
import java.util.UUID;

public interface CancelEventUseCase {
   Event execute (String id, UUID userId);
}
