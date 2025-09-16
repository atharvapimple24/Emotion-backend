package com.finalproject.MultilinguialAiApp.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "festival")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Festival {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String culture;
    private Date date;
    private String description;

    @OneToMany(mappedBy = "festival", cascade = CascadeType.ALL)
    private List<CulturalTemplate> templates = new ArrayList<>();
}

