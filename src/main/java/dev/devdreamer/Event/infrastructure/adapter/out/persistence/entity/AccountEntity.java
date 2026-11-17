package dev.devdreamer.Event.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tb_account")
public class AccountEntity {
    @Id public UUID id;
    @Column(name = "user_id", nullable = false, unique = true) public UUID userId;
    @Column(nullable = false, length = 100) public String name;
    @Column(nullable = false, length = 100) public String lastname;
    @Column(nullable = false) public LocalDate birthdate;
    protected AccountEntity() {}
    public AccountEntity(UUID id, UUID userId, String name, String lastname, LocalDate birthdate) {
        this.id = id; this.userId = userId; this.name = name; this.lastname = lastname; this.birthdate = birthdate;
    }
}
