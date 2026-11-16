package dev.devdreamer.Event.application.port.out;

import java.time.Instant;

public interface AccessTokenIssuer {
    IssuedToken issue(String subject);
    record IssuedToken(String value, String tokenId, Instant expiresAt) {}
}
