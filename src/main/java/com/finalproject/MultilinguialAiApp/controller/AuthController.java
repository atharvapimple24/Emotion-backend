package com.finalproject.MultilinguialAiApp.controller;

import com.finalproject.MultilinguialAiApp.model.dto.AuthResponse;
import com.finalproject.MultilinguialAiApp.model.dto.LoginRequest;
import com.finalproject.MultilinguialAiApp.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
