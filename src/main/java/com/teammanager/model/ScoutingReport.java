package com.teammanager.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "scouting_reports")
@EntityListeners(AuditingEntityListener.class)
public class ScoutingReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scout_id", nullable = false)
    private User scout;

    private LocalDateTime reportDate;
    
    private String competition;
    
    private String opponent;
    
    private String matchDate;
    
    private String position;
    
    private Integer minutesPlayed;
    
    // Technical abilities
    private Integer passing;
    private Integer shooting;
    private Integer dribbling;
    private Integer tackling;
    private Integer heading;
    private Integer crossing;
    
    // Physical attributes
    private Integer pace;
    private Integer strength;
    private Integer stamina;
    private Integer agility;
    
    // Mental attributes
    private Integer positioning;
    private Integer teamwork;
    private Integer leadership;
    private Integer decisionMaking;
    
    // Overall rating
    private Integer overallRating;
    
    private String strengths;
    
    private String weaknesses;
    
    private String recommendations;
    
    private String notes;
    
    private String status; // DRAFT, COMPLETED, ARCHIVED
    
    private String videoLinks;
    
    private String attachments;

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