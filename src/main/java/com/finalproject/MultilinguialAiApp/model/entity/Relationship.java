package com.finalproject.MultilinguialAiApp.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "relationship")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Relationship {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String type; // e.g., mother, father
    private String name;

    @ManyToMany
    @JoinTable(
            name = "relationship_trait_map",
            joinColumns = @JoinColumn(name = "relationship_id"),
            inverseJoinColumns = @JoinColumn(name = "trait_id")
    )
    private Set<PersonalityTrait> traits = new HashSet<>();
}

