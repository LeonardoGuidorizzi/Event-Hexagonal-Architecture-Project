package dev.devdreamer.Event.infrastructure.adapter.out.persistence.repository;

import dev.devdreamer.Event.infrastructure.adapter.out.persistence.entity.EventParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface EventParticipantJpaRepository extends JpaRepository<EventParticipantEntity, UUID> {
    boolean existsByEventIdAndUserId(UUID eventId, UUID userId);
}
