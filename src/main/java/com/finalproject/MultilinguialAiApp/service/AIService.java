package com.finalproject.MultilinguialAiApp.service;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
public class AIService {

    private final CacheService cacheService;
    private final OpenAIClient openAiClient; // nullable if mock mode
    private final boolean mockEnabled;

    // Token limits by tier
    private static final Map<String, Integer> TIER_TOKEN_LIMITS = Map.of(
            "FREE", 200,
            "LITE", 500,
            "PRO", 1500
    );

    public AIService(CacheService cacheService,
                     @Value("${ai.mock:true}") boolean mockEnabled) {
        this.cacheService = cacheService;
        this.mockEnabled = mockEnabled;

        OpenAIClient client = null;
        if (!mockEnabled) {
            try {
                client = OpenAIOkHttpClient.fromEnv();
                log.info("OpenAI client initialized");
            } catch (Exception e) {
                log.warn("OpenAI client not initialized (mock mode active). Error: {}", e.getMessage());
            }
        }
        this.openAiClient = client;
    }

    public String generateResponse(String systemPrompt,
                                   String userPrompt,
                                   String userId,
                                   String tier,
                                   boolean useMock) {
        String cacheKey = "ai:" + userId + ":" + userPrompt.hashCode();

        // 1. Try cache
        String cached = cacheService.get(cacheKey);
        if (cached != null) {
            return cached;
        }

        // 2. Force mock
        if (mockEnabled || useMock) {
            String response = "[MOCK_AI_RESPONSE] " + userPrompt;
            cacheService.put(cacheKey, response);
            return response;
        }

        // 3. Call OpenAI API (if available)
        if (openAiClient == null) {
            return "[ERROR] OpenAI client not configured.";
        }

        int maxTokens = TIER_TOKEN_LIMITS.getOrDefault(tier.toUpperCase(), 200);

        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .model(ChatModel.GPT_4_TURBO)
                .temperature(0.7)
                .maxCompletionTokens(maxTokens)
                .addSystemMessage(systemPrompt)
                .addUserMessage(userPrompt)
                .build();

        String aiResponse = callWithRetry(params);

        // 4. Post-processing
        aiResponse = filterProfanity(aiResponse);
        aiResponse = enforceLength(aiResponse, 400);

        // 5. Cache it
        cacheService.put(cacheKey, aiResponse);

        return aiResponse;
    }

    private String callWithRetry(ChatCompletionCreateParams params) {
        int retries = 3;
        long backoff = 1000;

        for (int attempt = 1; attempt <= retries; attempt++) {
            try {
                ChatCompletion chatCompletion = openAiClient.chat().completions().create(params);
                return chatCompletion.choices().get(0).message().content().orElse("");
            } catch (Exception e) {
                log.warn("OpenAI call failed (attempt {}/{}): {}", attempt, retries, e.getMessage());
                if (attempt == retries) throw new RuntimeException("AI service unavailable", e);

                try {
                    Thread.sleep(backoff + ThreadLocalRandom.current().nextInt(500));
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Interrupted during retry backoff", ie);
                }
                backoff *= 2;
            }
        }
        return "[ERROR] AI service unavailable.";
    }

    private String filterProfanity(String text) {
        List<String> banned = List.of("badword1", "badword2");
        String clean = text;
        for (String word : banned) {
            clean = clean.replaceAll("(?i)" + word, "***");
        }
        return clean;
    }

    private String enforceLength(String text, int maxChars) {
        if (text.length() <= maxChars) return text;
        return text.substring(0, maxChars) + "...";
    }
}
