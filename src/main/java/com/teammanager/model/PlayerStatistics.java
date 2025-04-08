package com.teammanager.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
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
    private Integer goals;
    private Integer assists;
    private Integer yellowCards;
    private Integer redCards;
    private Integer shotsTotal;
    private Integer shotsOnTarget;
    private Integer passes;
    private Double passAccuracy;
    private Integer tackles;
    private Integer interceptions;
    private Integer foulsCommitted;
    private Integer foulsReceived;
    private Integer saves; // for goalkeepers
    private Integer goalsAgainst; // for goalkeepers
    private Integer cleanSheets; // for goalkeepers

    @Column(columnDefinition = "TEXT")
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