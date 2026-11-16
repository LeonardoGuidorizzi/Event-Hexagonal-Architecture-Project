package dev.devdreamer.Event.infrastructure.adapter.in.presentation.dto;

import java.time.Instant;

public record TokenResponse(String accessToken, String tokenType, Instant expiresAt) {}
