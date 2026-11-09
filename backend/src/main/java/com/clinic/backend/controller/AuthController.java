
package com.clinic.backend.controller;

import com.clinic.backend.dto.request.LoginRequest;
import com.clinic.backend.dto.request.RegisterRequest;
import com.clinic.backend.dto.response.LoginResponse;
import com.clinic.backend.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // User registration
    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        LoginResponse response = authService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // User login
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {
        LoginResponse response = authService.login(request);

        return ResponseEntity.ok(response);
    }

    // Get currently authenticated user
    @GetMapping("/me")
    public ResponseEntity<Map<String, String>> getCurrentUser(
            Authentication authentication
    ) {
        String role = authentication.getAuthorities()
                .stream()
                .findFirst()
                .map(authority -> authority.getAuthority())
                .orElse("UNKNOWN");

        if (role.startsWith("ROLE_")) {
            role = role.substring(5);
        }

        Map<String, String> userInfo = Map.of(
                "username", authentication.getName(),
                "role", role,
                "message", "Authenticated user information"
        );

        return ResponseEntity.ok(userInfo);
    }
}
