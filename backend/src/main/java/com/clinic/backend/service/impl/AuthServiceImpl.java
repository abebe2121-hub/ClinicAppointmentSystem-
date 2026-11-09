
package com.clinic.backend.service.impl;

import com.clinic.backend.dto.request.LoginRequest;
import com.clinic.backend.dto.request.RegisterRequest;
import com.clinic.backend.dto.response.LoginResponse;
import com.clinic.backend.entity.User;
import com.clinic.backend.repository.UserRepository;
import com.clinic.backend.security.JwtService;
import com.clinic.backend.service.AuthService;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public LoginResponse register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Username already exists"
            );
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email already exists"
            );
        }

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Public registration creates PATIENT accounts only
        user.setRole("PATIENT");

        User savedUser = userRepository.save(user);

        String token = jwtService.generateToken(savedUser.getUsername());

        return new LoginResponse(
                token,
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getRole(),
                "Registration successful"
        );
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.UNAUTHORIZED,
                                "Invalid username or password"
                        )
                );

        // Check whether the account is active
        if (!"ACTIVE".equalsIgnoreCase(user.getStatus())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Your account is inactive"
            );
        }

        // Check whether the stored password hash exists
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid username or password"
            );
        }

        // Verify password using BCrypt
        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid username or password"
            );
        }

        String token = jwtService.generateToken(user.getUsername());

        return new LoginResponse(
                token,
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                "Login successful"
        );
    }
}
