package com.finalproject.MultilinguialAiApp.service;

import com.finalproject.MultilinguialAiApp.config.JwtTokenProvider;
import com.finalproject.MultilinguialAiApp.model.dto.*;
import com.finalproject.MultilinguialAiApp.model.entity.User;
import com.finalproject.MultilinguialAiApp.model.mapper.UserMapper;
import com.finalproject.MultilinguialAiApp.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        User user = UserMapper.toEntity(request, passwordEncoder.encode(request.getPassword()));
        user = userRepository.save(user);

        String token = jwtTokenProvider.generateToken(user.getId(), user.getEmail());
        return AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .expiresIn(jwtTokenProvider.getExpirySeconds())
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtTokenProvider.generateToken(user.getId(), user.getEmail());
        return AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .expiresIn(jwtTokenProvider.getExpirySeconds())
                .build();
    }
}
