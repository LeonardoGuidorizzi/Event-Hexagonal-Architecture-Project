package dev.devdreamer.Event.infrastructure.adapter.out.security;

import dev.devdreamer.Event.application.port.out.RevokedTokenRepository;
import org.springframework.security.oauth2.jwt.*;

public class RevocationCheckingJwtDecoder implements JwtDecoder {
    private final JwtDecoder delegate;
    private final RevokedTokenRepository revokedTokens;
    public RevocationCheckingJwtDecoder(JwtDecoder delegate, RevokedTokenRepository revokedTokens) {
        this.delegate = delegate; this.revokedTokens = revokedTokens;
    }
    @Override public Jwt decode(String token) {
        Jwt jwt = delegate.decode(token);
        if (jwt.getId() == null || revokedTokens.isRevoked(jwt.getId())) throw new BadJwtException("Token is invalid or revoked");
        return jwt;
    }
}
