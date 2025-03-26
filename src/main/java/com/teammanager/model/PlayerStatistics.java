package com.teammanager.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "player_statistics")
@EntityListeners(AuditingEntityListener.class)
public class PlayerStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    private Match match;

    private Integer minutesPlayed;
    
    // Offensive statistics
    private Integer goals;
    private Integer assists;
    private Integer shots;
    private Integer shotsOnTarget;
    private Integer passes;
    private Integer passesCompleted;
    private Integer crosses;
    private Integer crossesCompleted;
    
    // Defensive statistics
    private Integer tackles;
    private Integer tacklesWon;
    private Integer interceptions;
    private Integer clearances;
    private Integer blocks;
    private Integer fouls;
    private Integer foulsSuffered;
    
    // Physical statistics
    private Double distanceCovered;
    private Integer sprints;
    private Integer duels;
    private Integer duelsWon;
    
    // Rating
    private Double rating;
    
    // Notes
    private String notes;

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