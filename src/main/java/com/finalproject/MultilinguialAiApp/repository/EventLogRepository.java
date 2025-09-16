package com.finalproject.MultilinguialAiApp.repository;

import com.finalproject.MultilinguialAiApp.model.entity.EventLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface EventLogRepository extends JpaRepository<EventLog, UUID> {
    List<EventLog> findByUserId(UUID userId);
}
