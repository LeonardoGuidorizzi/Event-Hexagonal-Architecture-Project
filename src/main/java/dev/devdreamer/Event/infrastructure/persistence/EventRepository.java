package dev.devdreamer.Event.infrastructure.bancoDeDados;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<EventEntity, String> {
}
