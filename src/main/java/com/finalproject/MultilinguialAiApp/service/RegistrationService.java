package com.finalproject.MultilinguialAiApp.service;

import com.finalproject.MultilinguialAiApp.config.JwtTokenProvider;
import com.finalproject.MultilinguialAiApp.model.dto.AuthResponse;
import com.finalproject.MultilinguialAiApp.model.dto.RegisterRequest;
import com.finalproject.MultilinguialAiApp.model.entity.User;
import com.finalproject.MultilinguialAiApp.model.mapper.UserMapper;
import com.finalproject.MultilinguialAiApp.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public RegistrationService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        User user = UserMapper.toEntity(request, passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return null;
    }
}

