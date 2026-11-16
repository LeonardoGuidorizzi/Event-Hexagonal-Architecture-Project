package dev.devdreamer.Event.application.usecases;

import dev.devdreamer.Event.application.port.in.SignUpUseCase;
import dev.devdreamer.Event.application.port.out.*;
import dev.devdreamer.Event.domain.Account;
import dev.devdreamer.Event.domain.User;
import dev.devdreamer.Event.domain.exception.EmailAlreadyRegisteredException;
import java.util.UUID;

public class SignUpUseCaseImpl implements SignUpUseCase {
    private final UserRepository users;
    private final AccountRepository accounts;
    private final PasswordHasher passwordHasher;
    private final AccessTokenIssuer tokenIssuer;

    public SignUpUseCaseImpl(UserRepository users, AccountRepository accounts,
                             PasswordHasher passwordHasher, AccessTokenIssuer tokenIssuer) {
        this.users = users;
        this.accounts = accounts;
        this.passwordHasher = passwordHasher;
        this.tokenIssuer = tokenIssuer;
    }

    @Override
    public SignUpResult execute(SignUpCommand command) {
        String email = User.normalizeEmail(command.email());
        if (users.findByEmail(email).isPresent()) throw new EmailAlreadyRegisteredException();
        validate(command);
        User user = users.save(new User(UUID.randomUUID(), email, passwordHasher.hash(command.password()), null));
        accounts.save(new Account(UUID.randomUUID(), user.id(), command.name().trim(), command.lastname().trim(), command.birthdate()));
        var token = tokenIssuer.issue(email);
        return new SignUpResult(token.value(), "Bearer", token.expiresAt());
    }

    private void validate(SignUpCommand command) {
        if (command.name() == null || command.name().isBlank() || command.name().length() > 100
                || command.lastname() == null || command.lastname().isBlank() || command.lastname().length() > 100) {
            throw new IllegalArgumentException("Name and lastname are required and must be at most 100 characters");
        }
        if (command.birthdate() == null || command.birthdate().isAfter(java.time.LocalDate.now())) {
            throw new IllegalArgumentException("Birthdate must be provided and cannot be in the future");
        }
        if (command.password() == null || command.password().length() < 8
                || command.password().getBytes(java.nio.charset.StandardCharsets.UTF_8).length > 72) {
            throw new IllegalArgumentException("Password must be at least 8 characters and no more than 72 UTF-8 bytes");
        }
    }
}
