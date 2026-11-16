package dev.devdreamer.Event.infrastructure.adapter.in.presentation.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record SignUpRequest(
        @NotBlank @Size(max = 100) String name,
        @NotBlank @Size(max = 100) String lastname,
        @NotNull @PastOrPresent LocalDate birthdate,
        @NotBlank @Email @Size(max = 320) String email,
        @NotBlank @Size(min = 8, max = 72) String password
) {}
