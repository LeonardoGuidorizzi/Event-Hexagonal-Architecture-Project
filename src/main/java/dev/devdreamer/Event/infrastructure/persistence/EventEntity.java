package dev.devdreamer.Event.infrastructure.persistence;

import dev.devdreamer.Event.core.enums.EventType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_event")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 255, nullable = false)
    private String name;
    @Column(length = 255, nullable = false)
    private String description;
    @Column(length = 255, nullable = false)
    private String identifier;
    @Column(length = 255, nullable = false)
    private String local;
    @Column(length = 255, nullable = false)
    private String host;
    @Column(length = 255, nullable = false)
    private Integer capacity;
    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    private EventType eventType;
    @Column(name = "starts_at")
    private LocalDateTime start;
    @Column(name = "ends_at")
    private LocalDateTime end;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

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
