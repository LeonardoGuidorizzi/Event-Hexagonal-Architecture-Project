package dev.devdreamer.Event.application.port.in;

import java.time.Instant;

public interface LogoutUseCase {
    void execute(String tokenId, Instant expiresAt);
}
