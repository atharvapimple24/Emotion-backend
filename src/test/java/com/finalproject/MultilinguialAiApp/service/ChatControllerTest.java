package com.finalproject.MultilinguialAiApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.MultilinguialAiApp.config.JwtTokenProvider;
import com.finalproject.MultilinguialAiApp.controller.ChatController;
import com.finalproject.MultilinguialAiApp.model.dto.ChatMessageRequest;
import com.finalproject.MultilinguialAiApp.model.entity.User;
import com.finalproject.MultilinguialAiApp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ChatController.class)
class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private com.finalproject.MultilinguialAiApp.service.ChatService chatService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @WithMockUser // bypass JWT for now
    void testSendMessage() throws Exception {
        ChatMessageRequest req = ChatMessageRequest.builder()
                .message("Hello")
                .relationshipId(UUID.randomUUID())
                .build();

        mockMvc.perform(post("/api/chat/message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").exists());
    }
}
