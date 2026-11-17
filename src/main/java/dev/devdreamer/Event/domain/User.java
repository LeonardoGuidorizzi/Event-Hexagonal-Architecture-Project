package dev.devdreamer.Event.domain;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

public class User {
    private final UUID id;
    private final String email;
    private final String passwordHash;
    private final LocalDateTime createdAt;

    public User(UUID id, String email, String passwordHash, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.createdAt = createdAt;
    }

    public UUID id() { return id; }
    public String email() { return email; }
    public String passwordHash() { return passwordHash; }
    public LocalDateTime createdAt() { return createdAt; }

    public static String normalizeEmail(String email) {
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email is required");
        return email.trim().toLowerCase(Locale.ROOT);
    }
}
