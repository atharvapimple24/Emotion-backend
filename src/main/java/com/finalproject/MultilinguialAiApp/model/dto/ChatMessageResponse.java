package com.finalproject.MultilinguialAiApp.model.dto;

import lombok.*;

import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageResponse {
    private UUID conversationId;
    private String text;
    private String language;
    private Map<String, Object> meta; // flexible extra info (festival, tone, etc.)
}
