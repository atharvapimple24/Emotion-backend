package com.finalproject.MultilinguialAiApp.repository;

import com.finalproject.MultilinguialAiApp.model.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface ConversationRepository extends JpaRepository<Conversation, UUID> {
    List<Conversation> findByUserId(UUID userId);
}
