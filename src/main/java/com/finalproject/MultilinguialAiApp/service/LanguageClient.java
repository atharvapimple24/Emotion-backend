package com.finalproject.MultilinguialAiApp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class LanguageClient {

    private final RestTemplate restTemplate;
    private final String languageServiceUrl;

    public LanguageClient(RestTemplate restTemplate,
                          @Value("${external.language-service.url}") String languageServiceUrl) {
        this.restTemplate = restTemplate;
        this.languageServiceUrl = languageServiceUrl;
    }

    @SuppressWarnings("unchecked")
    public LanguageResult processText(String text) {
        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    languageServiceUrl + "/api/language/process",
                    Map.of("text", text),
                    Map.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> body = response.getBody();
                return LanguageResult.builder()
                        .primaryLanguage((String) body.get("primary_language"))
                        .script((String) body.get("script"))
                        .culturalKeywords((List<String>) body.getOrDefault("cultural_keywords", Collections.emptyList()))
                        .build();
            }
        } catch (Exception e) {
            log.warn("Language service unavailable, falling back. Error: {}", e.getMessage());
        }

        // fallback
        return fallback(text);
    }

    private LanguageResult fallback(String text) {
        return LanguageResult.builder()
                .primaryLanguage("unknown")
                .script("unknown")
                .culturalKeywords(Collections.emptyList())
                .build();
    }

    @lombok.Builder
    @lombok.Data
    public static class LanguageResult {
        private String primaryLanguage;
        private String script;
        private List<String> culturalKeywords;
    }
}
