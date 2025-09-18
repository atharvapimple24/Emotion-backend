package com.finalproject.MultilinguialAiApp.service;

import com.finalproject.MultilinguialAiApp.model.entity.Festival;
import com.finalproject.MultilinguialAiApp.model.entity.PersonalityTrait;
import com.finalproject.MultilinguialAiApp.model.entity.Relationship;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PromptBuilderTest {

    @Test
    void testGaneshChaturthiPrompt() {
        PromptBuilder builder = new PromptBuilder();

        // Mock inputs
        String userMessage = "Aai, आज गणपती आहे का?";
        LanguageClient.LanguageResult lang = LanguageClient.LanguageResult.builder()
                .primaryLanguage("Marathi")
                .script("Devanagari")
                .culturalKeywords(List.of("गणपती", "आज"))
                .build();

        // Convert LocalDate to Date for the Festival object
        LocalDate localDate = LocalDate.of(2024, 9, 19);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Festival festival = Festival.builder()
                .id(UUID.randomUUID())
                .name("Ganesh Chaturthi (Day 3)")
                .culture("Maharashtra")
                .date(date) // Use the converted Date object here
                .description("Ganesh festival")
                .build();

        CulturalService.FestivalContext ctx =
                new CulturalService.FestivalContext(festival, "मी तुझ्यासाठी मोदक बनवले आहेत.");

        Relationship rel = Relationship.builder()
                .id(UUID.randomUUID())
                .type("Mother")
                .name("Aai")
                .build();

        PersonalityTrait caring = PersonalityTrait.builder()
                .id(UUID.randomUUID())
                .trait("caring")
                .description("Shows warmth and affection")
                .build();

        PromptBuilder.PromptPair pair = builder.buildPrompt(
                userMessage,
                lang,
                ctx,
                rel,
                List.of(caring)
        );

        // Assertions
        assertTrue(pair.systemPrompt().contains("Mother"));
        assertTrue(pair.systemPrompt().contains("Marathi"));
        assertTrue(pair.userPrompt().contains("Ganesh Chaturthi"));
        assertTrue(pair.userPrompt().contains("मोदक"));
    }
}
