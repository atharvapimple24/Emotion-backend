package com.finalproject.MultilinguialAiApp.repository;

import com.finalproject.MultilinguialAiApp.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}
