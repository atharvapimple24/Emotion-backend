package com.finalproject.MultilinguialAiApp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AIService {

    private final CacheService cacheService;

    public AIService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    public String generateResponse(String systemPrompt, String userPrompt, String userId, String tier, boolean useMock) {
        String cacheKey = "ai:" + userId + ":" + userPrompt.hashCode();

        // Try cache
        String cached = cacheService.get(cacheKey);
        if (cached != null) {
            return cached;
        }

        String response;
        if (useMock) {
            response = "[MOCK_AI_RESPONSE] " + userPrompt;
        } else {
            // TODO: integrate with OpenAI API later
            response = "[REAL_AI_RESPONSE] " + userPrompt;
        }

        cacheService.put(cacheKey, response);
        return response;
    }
}
