package com.finalproject.MultilinguialAiApp.repository;

import com.finalproject.MultilinguialAiApp.model.entity.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface RelationshipRepository extends JpaRepository<Relationship, UUID> {
    List<Relationship> findByUserId(UUID userId);
}
