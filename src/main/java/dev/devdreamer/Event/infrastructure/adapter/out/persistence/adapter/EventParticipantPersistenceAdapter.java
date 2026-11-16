package dev.devdreamer.Event.infrastructure.adapter.out.persistence.adapter;

import dev.devdreamer.Event.application.port.out.EventParticipantRepository;
import dev.devdreamer.Event.infrastructure.adapter.out.persistence.entity.EventParticipantEntity;
import dev.devdreamer.Event.infrastructure.adapter.out.persistence.repository.EventParticipantJpaRepository;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class EventParticipantPersistenceAdapter implements EventParticipantRepository {
    private final EventParticipantJpaRepository repository;
    public EventParticipantPersistenceAdapter(EventParticipantJpaRepository repository) { this.repository = repository; }
    @Override public boolean exists(String eventId, UUID userId) {
        return repository.existsByEventIdAndUserId(UUID.fromString(eventId), userId);
    }
    @Override public void save(String eventId, UUID userId) {
        repository.save(new EventParticipantEntity(null, UUID.fromString(eventId), userId, LocalDateTime.now()));
    }
}
