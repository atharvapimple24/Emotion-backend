package com.finalproject.MultilinguialAiApp.repository;

import com.finalproject.MultilinguialAiApp.model.entity.PersonalityTrait;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PersonalityTraitRepository extends JpaRepository<PersonalityTrait, UUID> {}