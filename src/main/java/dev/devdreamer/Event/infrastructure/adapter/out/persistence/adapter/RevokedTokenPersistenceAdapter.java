package dev.devdreamer.Event.infrastructure.adapter.out.persistence.adapter;

import dev.devdreamer.Event.application.port.out.RevokedTokenRepository;
import dev.devdreamer.Event.infrastructure.adapter.out.persistence.entity.RevokedTokenEntity;
import dev.devdreamer.Event.infrastructure.adapter.out.persistence.repository.RevokedTokenJpaRepository;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class RevokedTokenPersistenceAdapter implements RevokedTokenRepository {
    private final RevokedTokenJpaRepository repository;
    public RevokedTokenPersistenceAdapter(RevokedTokenJpaRepository repository) { this.repository = repository; }
    @Override public boolean isRevoked(String tokenId) { return repository.existsById(tokenId); }
    @Override public void revoke(String tokenId, Instant expiresAt) {
        if (expiresAt.isAfter(Instant.now())) repository.save(new RevokedTokenEntity(tokenId, LocalDateTime.ofInstant(expiresAt, ZoneOffset.UTC)));
    }
}
