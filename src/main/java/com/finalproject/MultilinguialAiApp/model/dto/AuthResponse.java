package com.finalproject.MultilinguialAiApp.model.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    private String token;
    private String tokenType; // e.g., "Bearer"
    private Long expiresIn;   // in seconds
}
