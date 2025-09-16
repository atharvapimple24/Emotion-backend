package com.finalproject.MultilinguialAiApp.model.entity;


import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "cultural_template")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CulturalTemplate {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "festival_id", nullable = false)
    private Festival festival;

    @Column(name = "template_text", nullable = false, columnDefinition = "TEXT")
    private String templateText;

    private String language; // ISO code
}

