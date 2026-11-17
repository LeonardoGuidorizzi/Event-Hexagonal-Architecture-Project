package dev.devdreamer.Event.domain;

import java.time.LocalDate;
import java.util.UUID;

public class Account {
    private final UUID id;
    private final UUID userId;
    private final String name;
    private final String lastname;
    private final LocalDate birthdate;

    public Account(UUID id, UUID userId, String name, String lastname, LocalDate birthdate) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.lastname = lastname;
        this.birthdate = birthdate;
    }

    public UUID id() { return id; }
    public UUID userId() { return userId; }
    public String name() { return name; }
    public String lastname() { return lastname; }
    public LocalDate birthdate() { return birthdate; }
}
