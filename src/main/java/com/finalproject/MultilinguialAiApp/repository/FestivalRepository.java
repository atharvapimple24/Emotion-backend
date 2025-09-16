package com.finalproject.MultilinguialAiApp.repository;

import com.finalproject.MultilinguialAiApp.model.entity.Festival;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface FestivalRepository extends JpaRepository<Festival, UUID> {}
