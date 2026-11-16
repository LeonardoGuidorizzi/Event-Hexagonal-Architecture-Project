package dev.devdreamer.Event.application.port.out;

import dev.devdreamer.Event.domain.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);
    User save(User user);
}
