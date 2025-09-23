package com.finalproject.MultilinguialAiApp.service;

import com.finalproject.MultilinguialAiApp.config.JwtTokenProvider;
import com.finalproject.MultilinguialAiApp.model.dto.*;
import com.finalproject.MultilinguialAiApp.model.entity.User;
import com.finalproject.MultilinguialAiApp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;
    private RegistrationService registrationService;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        jwtTokenProvider = new JwtTokenProvider("SuperSecretJwtKeySuperSecretJwtKey", 3600000);
        registrationService = new RegistrationService(userRepository, jwtTokenProvider);
    }

    @Test
    void testRegisterSuccess() {
        RegisterRequest req = new RegisterRequest("test@example.com", "password", "Test User");

        when(userRepository.findByEmail(req.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        AuthResponse response = registrationService.register(req);

        assertNotNull(response.getToken());
        assertEquals("Bearer", response.getTokenType());
    }

    @Test
    void testLoginSuccess() {
        String email = "test@example.com";
        String password = "password";
        String hashedPassword = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode(password);

        User user = User.builder()
                .id(UUID.randomUUID())
                .email(email)
                .passwordHash(hashedPassword)
                .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        LoginRequest req = new LoginRequest(email, password);
        AuthResponse response = authService.login(req);

        assertNotNull(response.getToken());
        assertEquals("Bearer", response.getTokenType());
    }
}
