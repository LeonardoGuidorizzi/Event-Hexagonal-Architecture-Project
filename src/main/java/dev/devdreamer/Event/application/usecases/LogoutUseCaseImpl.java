package dev.devdreamer.Event.application.usecases;

import dev.devdreamer.Event.application.port.in.LogoutUseCase;
import dev.devdreamer.Event.application.port.out.RevokedTokenRepository;

import java.time.Instant;

public class LogoutUseCaseImpl implements LogoutUseCase {
    private final RevokedTokenRepository revokedTokens;
    public LogoutUseCaseImpl(RevokedTokenRepository revokedTokens) { this.revokedTokens = revokedTokens; }
    @Override
    public void execute(String tokenId, Instant expiresAt) { revokedTokens.revoke(tokenId, expiresAt); }
}
