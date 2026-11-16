package dev.devdreamer.Event.application.port.out;

import java.util.UUID;

public interface EventParticipantRepository {
    boolean exists(String eventId, UUID userId);
    void save(String eventId, UUID userId);
}
