package dev.devdreamer.Event.infrastructure.adapter.out.security;

import dev.devdreamer.Event.application.port.out.PasswordHasher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SpringPasswordHasher implements PasswordHasher {
    private final PasswordEncoder encoder;
    public SpringPasswordHasher(PasswordEncoder encoder) { this.encoder = encoder; }
    @Override public String hash(String password) { return encoder.encode(password); }
    @Override public boolean matches(String password, String passwordHash) { return encoder.matches(password, passwordHash); }
}
