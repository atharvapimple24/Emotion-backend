package com.finalproject.MultilinguialAiApp.service;

import com.finalproject.MultilinguialAiApp.model.entity.PersonalityTrait;
import com.finalproject.MultilinguialAiApp.model.entity.Relationship;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PromptBuilder {

    public PromptPair buildPrompt(String userMessage,
                                  LanguageClient.LanguageResult language,
                                  CulturalService.FestivalContext festivalContext,
                                  Relationship relationship,
                                  List<PersonalityTrait> traits) {

        String relationshipType = relationship.getType(); // e.g., "mother"
        String personaTerms = traits != null
                ? traits.stream().map(PersonalityTrait::getTrait).collect(Collectors.joining(", "))
                : "caring";

        String systemPrompt = String.format(
                "You are a %s personality (%s). Respond in %s script (%s).",
                relationshipType,
                personaTerms,
                language.getPrimaryLanguage(),
                language.getScript()
        );

        StringBuilder userPrompt = new StringBuilder();
        userPrompt.append("User said: ").append(userMessage).append(". ");

        if (festivalContext != null) {
            userPrompt.append("Today is ").append(festivalContext.festival().getName()).append(". ");
            if (festivalContext.template() != null) {
                userPrompt.append("Use cultural reference: ").append(festivalContext.template()).append(". ");
            }
        }

        return new PromptPair(systemPrompt, userPrompt.toString().trim());
    }

    public record PromptPair(String systemPrompt, String userPrompt) {}
}
