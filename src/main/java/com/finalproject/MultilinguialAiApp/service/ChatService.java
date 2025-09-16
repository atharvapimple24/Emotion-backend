package com.finalproject.MultilinguialAiApp.service;

import com.finalproject.MultilinguialAiApp.model.dto.ChatMessageRequest;
import com.finalproject.MultilinguialAiApp.model.dto.ChatMessageResponse;
import com.finalproject.MultilinguialAiApp.model.entity.Conversation;
import com.finalproject.MultilinguialAiApp.model.entity.EventLog;
import com.finalproject.MultilinguialAiApp.model.entity.User;
//import com.finalproject.MultilinguialAiApp.repository.ConversationRepository;
import com.finalproject.MultilinguialAiApp.repository.ConversationRepository;
import com.finalproject.MultilinguialAiApp.repository.EventLogRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChatService {

    private final ConversationService conversationService;
    private final EventLogRepository eventLogRepository;

    public ChatService(ConversationService conversationService,
                       EventLogRepository eventLogRepository) {
        this.conversationService = conversationService;
        this.eventLogRepository = eventLogRepository;
    }

    @Transactional
    public ChatMessageResponse handleMessage(User user, ChatMessageRequest request) {
        // Ensure conversation exists or create new
        Conversation conversation = conversationService.getOrCreateConversation(user, request.getConversationId());

        // Log event
        EventLog log = EventLog.builder()
                .user(user)
                .conversation(conversation)
                .eventType("chat_message")
                .eventData(Map.of("message", request.getMessage(),
                        "relationshipId", request.getRelationshipId().toString()).toString())
                .build();
        eventLogRepository.save(log);

        // Return mocked response
        return ChatMessageResponse.builder()
                .text("[MOCK] received: " + request.getMessage())
                .language("unknown")
                .meta(Map.of("conversationId", conversation.getId().toString()))
                .build();
    }
}
