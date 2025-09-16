package com.finalproject.MultilinguialAiApp.model.mapper;

import com.finalproject.MultilinguialAiApp.model.dto.FestivalDTO;
import com.finalproject.MultilinguialAiApp.model.entity.Festival;

public class FestivalMapper {
    public static FestivalDTO toDTO(Festival entity) {
        return FestivalDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .culture(entity.getCulture())
                .date(entity.getDate())
                .description(entity.getDescription())
                .build();
    }
}

