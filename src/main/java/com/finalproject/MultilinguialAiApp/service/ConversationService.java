package com.finalproject.MultilinguialAiApp.service;

import com.finalproject.MultilinguialAiApp.model.entity.Conversation;
import com.finalproject.MultilinguialAiApp.model.entity.User;
import com.finalproject.MultilinguialAiApp.repository.ConversationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;

    public ConversationService(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    public Conversation getOrCreateConversation(User user, UUID conversationId) {
        if (conversationId != null) {
            Optional<Conversation> existing = conversationRepository.findById(conversationId);
            if (existing.isPresent()) {
                return existing.get();
            }
        }
        Conversation newConv = Conversation.builder()
                .user(user)
                .status("active")
                .build();
        return conversationRepository.save(newConv);
    }
}
