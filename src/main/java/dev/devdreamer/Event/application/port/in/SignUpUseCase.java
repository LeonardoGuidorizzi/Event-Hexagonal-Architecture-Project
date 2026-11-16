package dev.devdreamer.Event.application.port.in;

import java.time.Instant;
import java.time.LocalDate;

public interface SignUpUseCase {
    SignUpResult execute(SignUpCommand command);

    record SignUpCommand(String name, String lastname, LocalDate birthdate, String email, String password) {}
    record SignUpResult(String accessToken, String tokenType, Instant expiresAt) {}
}
