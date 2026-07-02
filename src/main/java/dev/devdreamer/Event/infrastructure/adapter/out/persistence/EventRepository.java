package dev.devdreamer.Event.infrastructure.adapter.out.persistence;

import dev.devdreamer.Event.infrastructure.adapter.out.persistence.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
}
