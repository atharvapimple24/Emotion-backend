package com.finalproject.MultilinguialAiApp.service;

import com.finalproject.MultilinguialAiApp.model.entity.CulturalTemplate;
import com.finalproject.MultilinguialAiApp.model.entity.Festival;
import com.finalproject.MultilinguialAiApp.repository.CulturalTemplateRepository;
import com.finalproject.MultilinguialAiApp.repository.FestivalRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class CulturalService {

    private final FestivalRepository festivalRepository;
    private final CulturalTemplateRepository templateRepository;

    public CulturalService(FestivalRepository festivalRepository,
                           CulturalTemplateRepository templateRepository) {
        this.festivalRepository = festivalRepository;
        this.templateRepository = templateRepository;
    }

    public Optional<FestivalContext> getFestivalContext(LocalDate date, String region, String language) {
        List<Festival> festivals = festivalRepository.findAll();
        return festivals.stream()
                .filter(f -> f.getDate() != null && f.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isEqual(date))
                .filter(f -> f.getCulture() != null && f.getCulture().equalsIgnoreCase(region))
                .findFirst()
                .map(f -> {
                    List<CulturalTemplate> templates = templateRepository.findAll();
                    String template = templates.stream()
                            .filter(t -> t.getFestival().getId().equals(f.getId()))
                            .filter(t -> t.getLanguage().equalsIgnoreCase(language))
                            .map(CulturalTemplate::getTemplateText)
                            .findFirst()
                            .orElse(null);
                    return new FestivalContext(f, template);
                });
    }

    public record FestivalContext(Festival festival, String template) {}
}
