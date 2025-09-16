package com.finalproject.MultilinguialAiApp.model.entity;


import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "conversation")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Conversation {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "started_at")
    private Date startedAt;

    @Column(name = "ended_at")
    private Date endedAt;

    private String status; // active, closed

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL)
    private List<EventLog> eventLogs = new ArrayList<>();
}

