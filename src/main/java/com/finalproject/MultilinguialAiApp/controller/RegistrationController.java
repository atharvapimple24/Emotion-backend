package com.finalproject.MultilinguialAiApp.controller;

import com.finalproject.MultilinguialAiApp.model.dto.RegisterRequest;
import com.finalproject.MultilinguialAiApp.service.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/register")
public class RegistrationController {

    public final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        registrationService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }
}

