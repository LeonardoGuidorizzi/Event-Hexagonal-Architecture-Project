package dev.devdreamer.Event.infrastructure.adapter.out.persistence;

import dev.devdreamer.Event.infrastructure.adapter.out.persistence.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventJpaRepository extends JpaRepository<EventEntity, UUID> {
}
