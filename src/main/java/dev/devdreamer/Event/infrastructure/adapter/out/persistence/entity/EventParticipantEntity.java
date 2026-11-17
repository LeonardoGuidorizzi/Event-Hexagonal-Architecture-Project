package dev.devdreamer.Event.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_event_participant", uniqueConstraints = @UniqueConstraint(name = "uq_event_participant_user", columnNames = {"event_id", "user_id"}))
public class EventParticipantEntity {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @Column(name = "event_id", nullable = false) private UUID eventId;
    @Column(name = "user_id", nullable = false) private UUID userId;
    @Column(name = "created_at", nullable = false) private LocalDateTime createdAt;
    protected EventParticipantEntity() {}
    public EventParticipantEntity(UUID id, UUID eventId, UUID userId, LocalDateTime createdAt) {
        this.id = id; this.eventId = eventId; this.userId = userId; this.createdAt = createdAt;
    }
}
