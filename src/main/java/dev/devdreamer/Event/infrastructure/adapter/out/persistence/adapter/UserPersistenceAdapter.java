package dev.devdreamer.Event.infrastructure.adapter.out.persistence.adapter;

import dev.devdreamer.Event.application.port.out.UserRepository;
import dev.devdreamer.Event.domain.User;
import dev.devdreamer.Event.infrastructure.adapter.out.persistence.entity.UserEntity;
import dev.devdreamer.Event.infrastructure.adapter.out.persistence.repository.UserJpaRepository;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class UserPersistenceAdapter implements UserRepository {
    private final UserJpaRepository repository;
    public UserPersistenceAdapter(UserJpaRepository repository) { this.repository = repository; }
    @Override public Optional<User> findByEmail(String email) { return repository.findByEmail(email).map(this::toDomain); }
    @Override public User save(User user) {
        return toDomain(repository.save(new UserEntity(user.id(), user.email(), user.passwordHash(), LocalDateTime.now())));
    }
    private User toDomain(UserEntity entity) { return new User(entity.id, entity.email, entity.passwordHash, entity.createdAt); }
}
