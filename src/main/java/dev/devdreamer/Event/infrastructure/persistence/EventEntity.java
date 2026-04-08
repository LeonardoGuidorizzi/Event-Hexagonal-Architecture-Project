package dev.devdreamer.Event.infrastructure.persistence;

import dev.devdreamer.Event.core.enums.EventType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_event")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            type = org.hibernate.id.uuid.UuidGenerator.class
    )
    private UUID id;
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
}
