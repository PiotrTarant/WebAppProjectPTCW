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

    // Games played and started
    private Integer gamesPlayed;
    private Integer gamesStarted;

    // Points
    private Integer points;

    // Field Goals
    private Integer fieldGoalsMade;
    private Integer fieldGoalsAttempted;
    private Double fieldGoalPercentage;

    // Free Throws
    private Integer freeThrowsMade;
    private Integer freeThrowsAttempted;
    private Double freeThrowPercentage;

    // Three-Point Field Goals
    private Integer threePointFieldGoalsMade;
    private Integer threePointFieldGoalsAttempted;
    private Double threePointFieldGoalPercentage;

    // Rebounds
    private Integer totalRebounds;
    private Integer offensiveRebounds;
    private Integer defensiveRebounds;

    // Other Basic Stats
    private Integer assists;
    private Integer steals;
    private Integer turnovers;
    private Integer blocks;
    private Integer personalFouls;
    private Integer technicalFouls;
    private Integer flagrantFouls;
    private Integer plusMinus;

    // Advanced Stats
    private Double efficiency;
    private Double trueShootingPercentage;
    private Double usageRate;
    private Double playerEfficiencyRating;
    private Double winShares;
    private Double boxPlusMinus;
    private Double valueOverReplacementPlayer;

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