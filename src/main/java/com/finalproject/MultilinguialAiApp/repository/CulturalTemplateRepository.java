package com.finalproject.MultilinguialAiApp.repository;

import com.finalproject.MultilinguialAiApp.model.entity.CulturalTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CulturalTemplateRepository extends JpaRepository<CulturalTemplate, UUID> {}
