package dev.devdreamer.Event.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_user")
public class UserEntity {
    @Id public UUID id;
    @Column(nullable = false, unique = true, length = 320) public String email;
    @Column(name = "password_hash", nullable = false) public String passwordHash;
    @Column(name = "created_at", nullable = false) public LocalDateTime createdAt;
    protected UserEntity() {}
    public UserEntity(UUID id, String email, String passwordHash, LocalDateTime createdAt) {
        this.id = id; this.email = email; this.passwordHash = passwordHash; this.createdAt = createdAt;
    }
}
