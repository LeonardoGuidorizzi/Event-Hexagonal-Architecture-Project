package dev.devdreamer.Event.infrastructure.adapter.out.persistence.repository;

import dev.devdreamer.Event.infrastructure.adapter.out.persistence.entity.RevokedTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RevokedTokenJpaRepository extends JpaRepository<RevokedTokenEntity, String> {}
