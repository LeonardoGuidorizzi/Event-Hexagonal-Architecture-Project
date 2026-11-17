package dev.devdreamer.Event.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_revoked_token")
public class RevokedTokenEntity {
    @Id @Column(name = "token_id", length = 36) private String tokenId;
    @Column(name = "expires_at", nullable = false) private LocalDateTime expiresAt;
    protected RevokedTokenEntity() {}
    public RevokedTokenEntity(String tokenId, LocalDateTime expiresAt) { this.tokenId = tokenId; this.expiresAt = expiresAt; }
}
