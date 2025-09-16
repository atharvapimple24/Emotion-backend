package com.finalproject.MultilinguialAiApp.model.mapper;

import com.finalproject.MultilinguialAiApp.model.dto.ChatMessageResponse;
import com.finalproject.MultilinguialAiApp.model.entity.Conversation;

import java.util.Map;

public class ChatMapper {
    public static ChatMessageResponse toResponse(Conversation conversation, String text, String language, Map<String,Object> meta) {
        return ChatMessageResponse.builder()
                .conversationId(conversation.getId())
                .text(text)
                .language(language)
                .meta(meta)
                .build();
    }
}
