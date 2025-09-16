package com.finalproject.MultilinguialAiApp.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageRequest {
    @NotBlank(message = "Message cannot be empty")
    private String message;

    @NotNull(message = "Relationship ID is required")
    private UUID relationshipId;

    private UUID conversationId; // optional: continue existing conversation
}