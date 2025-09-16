package com.finalproject.MultilinguialAiApp.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "personality_trait")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalityTrait {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String trait;

    private String description;

    @ManyToMany(mappedBy = "traits")
    private Set<Relationship> relationships = new HashSet<>();
}
