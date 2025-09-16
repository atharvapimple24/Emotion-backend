package com.finalproject.MultilinguialAiApp.controller;

import com.finalproject.MultilinguialAiApp.model.dto.ChatMessageRequest;
import com.finalproject.MultilinguialAiApp.model.dto.ChatMessageResponse;
import com.finalproject.MultilinguialAiApp.model.entity.User;
import com.finalproject.MultilinguialAiApp.service.ChatService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/message")
    public ResponseEntity<ChatMessageResponse> sendMessage(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody ChatMessageRequest request) {
        return ResponseEntity.ok(chatService.handleMessage(user, request));
    }
}
