package dev.devdreamer.Event.application.port.out;

import java.time.Instant;

public interface RevokedTokenRepository {
    boolean isRevoked(String tokenId);
    void revoke(String tokenId, Instant expiresAt);
}
