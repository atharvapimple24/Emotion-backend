package com.finalproject.MultilinguialAiApp.model.mapper;

import com.finalproject.MultilinguialAiApp.model.dto.RegisterRequest;
import com.finalproject.MultilinguialAiApp.model.entity.User;

public class UserMapper {
    public static User toEntity(RegisterRequest dto, String passwordHash) {
        return User.builder()
                .email(dto.getEmail())
                .passwordHash(passwordHash) // after encoding
                .displayName(dto.getDisplayName())
                .build();
    }
}
