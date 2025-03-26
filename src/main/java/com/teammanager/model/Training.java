package com.teammanager.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "trainings")
@EntityListeners(AuditingEntityListener.class)
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    private LocalDateTime startTime;
    
    private LocalDateTime endTime;
    
    private String location;
    
    private String type; // TACTICAL, TECHNICAL, PHYSICAL, RECOVERY, etc.
    
    private String description;
    
    private String objectives;
    
    private String equipment;
    
    private String weather;
    
    private String status; // SCHEDULED, IN_PROGRESS, COMPLETED, CANCELLED
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coach_id")
    private User coach;

    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL)
    private Set<TrainingAttendance> attendances = new HashSet<>();

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
} 