package dev.devdreamer.Event.application.port.out;

import java.time.Instant;
import java.util.UUID;

public interface AccessTokenIssuer {
    IssuedToken issue(String subject);
    default IssuedToken issue(String subject, UUID userId) { return issue(subject); }
    record IssuedToken(String value, String tokenId, Instant expiresAt) {}
}
