package dev.devdreamer.Event.application.usecases;

import dev.devdreamer.Event.application.port.in.LoginUseCase;
import dev.devdreamer.Event.application.port.in.SignUpUseCase;
import dev.devdreamer.Event.application.port.out.AccessTokenIssuer;
import dev.devdreamer.Event.application.port.out.AccountRepository;
import dev.devdreamer.Event.application.port.out.PasswordHasher;
import dev.devdreamer.Event.application.port.out.UserRepository;
import dev.devdreamer.Event.domain.Account;
import dev.devdreamer.Event.domain.User;
import dev.devdreamer.Event.domain.exception.EmailAlreadyRegisteredException;
import dev.devdreamer.Event.domain.exception.InvalidCredentialsException;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AuthUseCaseTests {
    @Test
    void signUpCreatesUserAndAccountAndReturnsBearerToken() {
        FakeUsers users = new FakeUsers();
        FakeAccounts accounts = new FakeAccounts();
        FakeTokens tokens = new FakeTokens();
        SignUpUseCase useCase = new SignUpUseCaseImpl(users, accounts, new FakeHasher(), tokens);
        LocalDate birthdate = LocalDate.of(1990, 1, 2);

        var result = useCase.execute(new SignUpUseCase.SignUpCommand(
                "  Jane ", " Doe ", birthdate, " Jane@Example.COM ", "password123"));

        assertEquals("token-1", result.accessToken());
        assertEquals("Bearer", result.tokenType());
        assertEquals(Instant.parse("2030-01-01T00:00:00Z"), result.expiresAt());
        assertEquals(1, users.saved.size());
        User user = users.saved.getFirst();
        assertEquals("jane@example.com", user.email());
        assertEquals("hash:password123", user.passwordHash());
        assertEquals(1, accounts.saved.size());
        Account account = accounts.saved.getFirst();
        assertEquals(user.id(), account.userId());
        assertEquals("Jane", account.name());
        assertEquals("Doe", account.lastname());
        assertEquals(birthdate, account.birthdate());
        assertEquals(List.of("jane@example.com"), tokens.subjects);
    }

    @Test
    void signUpRejectsDuplicateEmailWithoutCreatingRecords() {
        FakeUsers users = new FakeUsers();
        users.existing = new User(UUID.randomUUID(), "jane@example.com", "hash", LocalDateTime.now());
        FakeAccounts accounts = new FakeAccounts();
        SignUpUseCase useCase = new SignUpUseCaseImpl(users, accounts, new FakeHasher(), new FakeTokens());

        assertThrows(EmailAlreadyRegisteredException.class, () -> useCase.execute(new SignUpUseCase.SignUpCommand(
                "Jane", "Doe", LocalDate.of(1990, 1, 2), "Jane@Example.com", "password123")));
        assertTrue(users.saved.isEmpty());
        assertTrue(accounts.saved.isEmpty());
    }

    @Test
    void signUpRejectsInvalidPasswordAndFutureBirthdate() {
        SignUpUseCase useCase = new SignUpUseCaseImpl(new FakeUsers(), new FakeAccounts(), new FakeHasher(), new FakeTokens());
        assertThrows(IllegalArgumentException.class, () -> useCase.execute(new SignUpUseCase.SignUpCommand(
                "Jane", "Doe", LocalDate.of(1990, 1, 2), "jane@example.com", "short")));
        assertThrows(IllegalArgumentException.class, () -> useCase.execute(new SignUpUseCase.SignUpCommand(
                "Jane", "Doe", LocalDate.now().plusDays(1), "jane@example.com", "password123")));
    }

    @Test
    void signUpAcceptsMaximumNameLengthTodayBirthdateAndPasswordByteLimit() {
        FakeUsers users = new FakeUsers();
        FakeAccounts accounts = new FakeAccounts();
        SignUpUseCase useCase = new SignUpUseCaseImpl(users, accounts, new FakeHasher(), new FakeTokens());
        String maxName = "N".repeat(100);
        String maxUtf8Password = "€".repeat(24); // 24 characters and exactly 72 UTF-8 bytes

        assertDoesNotThrow(() -> useCase.execute(new SignUpUseCase.SignUpCommand(
                maxName, maxName, LocalDate.now(), "jane@example.com", maxUtf8Password)));

        assertEquals(maxName, accounts.saved.getFirst().name());
        assertEquals(maxName, accounts.saved.getFirst().lastname());
        assertEquals(1, users.saved.size());
    }

    @Test
    void signUpRejectsNamesOverLimitAndBlankNames() {
        SignUpUseCase useCase = new SignUpUseCaseImpl(new FakeUsers(), new FakeAccounts(), new FakeHasher(), new FakeTokens());
        LocalDate validBirthdate = LocalDate.of(1990, 1, 2);

        assertThrows(IllegalArgumentException.class, () -> useCase.execute(new SignUpUseCase.SignUpCommand(
                "N".repeat(101), "Doe", validBirthdate, "jane@example.com", "password123")));
        assertThrows(IllegalArgumentException.class, () -> useCase.execute(new SignUpUseCase.SignUpCommand(
                "Jane", "  ", validBirthdate, "jane@example.com", "password123")));
    }

    @Test
    void signUpRejectsMissingBirthdatePasswordAndPasswordsOverUtf8ByteLimit() {
        SignUpUseCase useCase = new SignUpUseCaseImpl(new FakeUsers(), new FakeAccounts(), new FakeHasher(), new FakeTokens());
        LocalDate validBirthdate = LocalDate.of(1990, 1, 2);

        assertThrows(IllegalArgumentException.class, () -> useCase.execute(new SignUpUseCase.SignUpCommand(
                "Jane", "Doe", null, "jane@example.com", "password123")));
        assertThrows(IllegalArgumentException.class, () -> useCase.execute(new SignUpUseCase.SignUpCommand(
                "Jane", "Doe", validBirthdate, "jane@example.com", null)));
        assertThrows(IllegalArgumentException.class, () -> useCase.execute(new SignUpUseCase.SignUpCommand(
                "Jane", "Doe", validBirthdate, "jane@example.com", "€".repeat(25))));
    }

    @Test
    void signUpRejectsBlankEmailBeforeSavingRecords() {
        FakeUsers users = new FakeUsers();
        FakeAccounts accounts = new FakeAccounts();
        SignUpUseCase useCase = new SignUpUseCaseImpl(users, accounts, new FakeHasher(), new FakeTokens());

        assertThrows(IllegalArgumentException.class, () -> useCase.execute(new SignUpUseCase.SignUpCommand(
                "Jane", "Doe", LocalDate.of(1990, 1, 2), "  ", "password123")));
        assertTrue(users.saved.isEmpty());
        assertTrue(accounts.saved.isEmpty());
    }

    @Test
    void loginNormalizesEmailAndReturnsBearerTokenWhenCredentialsAreValid() {
        FakeUsers users = new FakeUsers();
        users.existing = new User(UUID.randomUUID(), "jane@example.com", "hash:password123", LocalDateTime.now());
        FakeTokens tokens = new FakeTokens();
        LoginUseCase useCase = new LoginUseCaseImpl(users, new FakeHasher(), tokens);

        var result = useCase.execute(" Jane@Example.COM ", "password123");

        assertEquals("token-1", result.accessToken());
        assertEquals("Bearer", result.tokenType());
        assertEquals(Instant.parse("2030-01-01T00:00:00Z"), result.expiresAt());
        assertEquals(List.of("jane@example.com"), users.lookups);
        assertEquals(List.of("jane@example.com"), tokens.subjects);
    }

    @Test
    void loginRejectsUnknownUserAndWrongPassword() {
        FakeUsers unknownUsers = new FakeUsers();
        LoginUseCase unknownLogin = new LoginUseCaseImpl(unknownUsers, new FakeHasher(), new FakeTokens());
        assertThrows(InvalidCredentialsException.class, () -> unknownLogin.execute("missing@example.com", "password123"));

        FakeUsers users = new FakeUsers();
        users.existing = new User(UUID.randomUUID(), "jane@example.com", "hash:correct", LocalDateTime.now());
        FakeTokens tokens = new FakeTokens();
        LoginUseCase wrongPasswordLogin = new LoginUseCaseImpl(users, new FakeHasher(), tokens);
        assertThrows(InvalidCredentialsException.class, () -> wrongPasswordLogin.execute("jane@example.com", "incorrect"));
        assertTrue(tokens.subjects.isEmpty());
    }

    private static final class FakeUsers implements UserRepository {
        private User existing;
        private final List<User> saved = new ArrayList<>();
        private final List<String> lookups = new ArrayList<>();

        @Override
        public Optional<User> findByEmail(String email) {
            lookups.add(email);
            if (existing != null && existing.email().equals(email)) return Optional.of(existing);
            return Optional.empty();
        }

        @Override
        public User save(User user) {
            saved.add(user);
            existing = user;
            return user;
        }
    }

    private static final class FakeAccounts implements AccountRepository {
        private final List<Account> saved = new ArrayList<>();
        @Override public Account save(Account account) { saved.add(account); return account; }
    }

    private static final class FakeHasher implements PasswordHasher {
        @Override public String hash(String password) { return "hash:" + password; }
        @Override public boolean matches(String password, String passwordHash) { return passwordHash.equals(hash(password)); }
    }

    private static final class FakeTokens implements AccessTokenIssuer {
        private final List<String> subjects = new ArrayList<>();
        @Override public IssuedToken issue(String subject) {
            subjects.add(subject);
            int sequence = subjects.size();
            return new IssuedToken("token-" + sequence, "id-" + sequence, Instant.parse("2030-01-01T00:00:00Z"));
        }
    }
}
