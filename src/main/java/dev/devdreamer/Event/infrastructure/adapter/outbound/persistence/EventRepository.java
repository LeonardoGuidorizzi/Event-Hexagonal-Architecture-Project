package dev.devdreamer.Event.infrastructure.adapter.outbound.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
}
