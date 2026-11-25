package dev.devdreamer.Event.infrastructure.adapter.out.persistence.entity;

import dev.devdreamer.Event.domain.enums.EventStatus;
import dev.devdreamer.Event.domain.enums.EventType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_event")
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    public UUID id;
    @Column(length = 255, nullable = false)
    public String name;
    @Column(length = 255, nullable = false)
    public String description;
    @Column(length = 255, nullable = false)
    public String identifier;
    @Column(length = 255, nullable = false)
    public String local;
    @Column(length = 255, nullable = false)
    public String host;
    @Column(nullable = false)
    public Integer capacity;
    @Column(name = "registered_count", nullable = false)
    public Integer registeredCount;
    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    public EventType eventType;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public EventStatus status;
    @Column(name = "starts_at")
    public LocalDateTime start;
    @Column(name = "ends_at")
    public LocalDateTime end;
    @Column(name = "cancelled_at")
    public LocalDateTime cancelledAt;
    @Column(name = "created_at")
    public LocalDateTime createdAt;
    @Column(name = "updated_at")
    public LocalDateTime updatedAt;
    @Column(name = "creator_id")
    public UUID creatorId;

    public EventEntity() {}

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
