package dev.devdreamer.Event.application.port.in;

import java.time.Instant;

public interface LoginUseCase {
    LoginResult execute(String email, String password);
    record LoginResult(String accessToken, String tokenType, Instant expiresAt) {}
}
