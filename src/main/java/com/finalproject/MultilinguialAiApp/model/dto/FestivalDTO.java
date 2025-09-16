package com.finalproject.MultilinguialAiApp.model.dto;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FestivalDTO {
    private UUID id;
    private String name;
    private String culture;
    private Date date;
    private String description;
}
