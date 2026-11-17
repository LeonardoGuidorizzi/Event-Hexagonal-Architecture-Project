package dev.devdreamer.Event.application.usecases;

import dev.devdreamer.Event.application.port.in.LoginUseCase;
import dev.devdreamer.Event.application.port.out.AccessTokenIssuer;
import dev.devdreamer.Event.application.port.out.PasswordHasher;
import dev.devdreamer.Event.application.port.out.UserRepository;
import dev.devdreamer.Event.domain.User;
import dev.devdreamer.Event.domain.exception.InvalidCredentialsException;

public class LoginUseCaseImpl implements LoginUseCase {
    private final UserRepository users;
    private final PasswordHasher passwordHasher;
    private final AccessTokenIssuer tokenIssuer;

    public LoginUseCaseImpl(UserRepository users, PasswordHasher passwordHasher, AccessTokenIssuer tokenIssuer) {
        this.users = users;
        this.passwordHasher = passwordHasher;
        this.tokenIssuer = tokenIssuer;
    }

    @Override
    public LoginResult execute(String email, String password) {
        var user = users.findByEmail(User.normalizeEmail(email)).orElseThrow(InvalidCredentialsException::new);
        if (!passwordHasher.matches(password, user.passwordHash())) throw new InvalidCredentialsException();
        var token = tokenIssuer.issue(user.email());
        return new LoginResult(token.value(), "Bearer", token.expiresAt());
    }
}
