package dev.devdreamer.Event.application.port.in;

import java.util.UUID;

public interface DeleteEventUseCase {
    void execute(String id, UUID userId);
}
