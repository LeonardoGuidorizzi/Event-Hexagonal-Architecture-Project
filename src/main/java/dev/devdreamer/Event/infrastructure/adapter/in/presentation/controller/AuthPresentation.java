package dev.devdreamer.Event.infrastructure.adapter.in.presentation.controller;

import dev.devdreamer.Event.application.port.in.LoginUseCase;
import dev.devdreamer.Event.application.port.in.LogoutUseCase;
import dev.devdreamer.Event.application.port.in.SignUpUseCase;
import dev.devdreamer.Event.infrastructure.adapter.in.presentation.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("api/v1/auth")
public class AuthPresentation {
    private final SignUpUseCase signUpUseCase;
    private final LoginUseCase loginUseCase;
    private final LogoutUseCase logoutUseCase;
    public AuthPresentation(SignUpUseCase signUpUseCase, LoginUseCase loginUseCase, LogoutUseCase logoutUseCase) {
        this.signUpUseCase = signUpUseCase; this.loginUseCase = loginUseCase; this.logoutUseCase = logoutUseCase;
    }
    @PostMapping("/signup")
    @Transactional
    @Operation(summary = "Sign up")
    public ResponseEntity<TokenResponse> signUp(@Valid @RequestBody SignUpRequest request) {
        var result = signUpUseCase.execute(new SignUpUseCase.SignUpCommand(request.name(), request.lastname(),
                request.birthdate(), request.email(), request.password()));
        return ResponseEntity.status(HttpStatus.CREATED).body(new TokenResponse(result.accessToken(), result.tokenType(), result.expiresAt()));
    }
    @PostMapping("/login")
    @Operation(summary = "Log in")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        var result = loginUseCase.execute(request.email(), request.password());
        return ResponseEntity.ok(new TokenResponse(result.accessToken(), result.tokenType(), result.expiresAt()));
    }
    @PostMapping("/logout")
    @Transactional
    @Operation(summary = "Log out and revoke the current access token")
    public ResponseEntity<Map<String, String>> logout(JwtAuthenticationToken authentication) {
        logoutUseCase.execute(authentication.getToken().getId(), authentication.getToken().getExpiresAt());
        return ResponseEntity.ok(Map.of("message", "Logged out"));
    }
}
