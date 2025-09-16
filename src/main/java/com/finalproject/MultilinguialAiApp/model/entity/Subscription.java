package com.finalproject.MultilinguialAiApp.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "subscription")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String tier; // FREE, LITE, PRO

    @Column(columnDefinition = "jsonb")
    private String featuresJson;

    private Double priceUsd;

    @Column(name = "created_at")
    private Date createdAt;

    @OneToMany(mappedBy = "subscription")
    private List<User> users = new ArrayList<>();
}

