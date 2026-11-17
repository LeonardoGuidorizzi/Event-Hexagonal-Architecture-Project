package dev.devdreamer.Event.infrastructure.adapter.out.security;

import dev.devdreamer.Event.application.port.out.AccessTokenIssuer;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.UUID;

@Component
public class JwtAccessTokenIssuer implements AccessTokenIssuer {
    private final JwtEncoder encoder;
    public JwtAccessTokenIssuer(JwtEncoder encoder) { this.encoder = encoder; }
    @Override public IssuedToken issue(String subject) {
        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(900);
        String tokenId = UUID.randomUUID().toString();
        var claims = JwtClaimsSet.builder().issuer("event-api").subject(subject).issuedAt(now).expiresAt(expiresAt).id(tokenId).build();
        var token = encoder.encode(JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims));
        return new IssuedToken(token.getTokenValue(), tokenId, expiresAt);
    }
}
