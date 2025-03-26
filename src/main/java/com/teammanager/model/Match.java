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
@Table(name = "matches")
@EntityListeners(AuditingEntityListener.class)
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_team_id", nullable = false)
    private Team homeTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "away_team_id", nullable = false)
    private Team awayTeam;

    private LocalDateTime matchDate;
    
    private String venue;
    
    private String competition;
    
    private String season;
    
    private Integer homeScore;
    
    private Integer awayScore;
    
    private String status; // SCHEDULED, IN_PROGRESS, COMPLETED, CANCELLED, POSTPONED
    
    private String referee;
    
    private Integer attendance;
    
    private String weather;
    
    private String pitchCondition;
    
    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL)
    private Set<PlayerStatistics> playerStatistics = new HashSet<>();

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL)
    private Set<MatchEvent> events = new HashSet<>();

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