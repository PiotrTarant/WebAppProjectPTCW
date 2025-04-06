package com.teammanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "players")
@EntityListeners(AuditingEntityListener.class)
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String firstName;

    @NotBlank
    @Size(max = 50)
    private String lastName;

    @NotNull
    private LocalDate dateOfBirth;

    @Size(max = 100)
    private String nationality;

    @Size(max = 20)
    private String jerseyNumber;

    @Size(max = 50)
    private String position;

    private Double height; // in centimeters

    private Double weight; // in kilograms

    @Size(max = 500)
    private String biography;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private Set<PlayerStatistics> statistics = new HashSet<>();

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private Set<Absence> absences = new HashSet<>();

    @ManyToMany(mappedBy = "players")
    private Set<Training> trainings = new HashSet<>();

    private String photoUrl;

    @Enumerated(EnumType.STRING)
    private PlayerStatus status = PlayerStatus.ACTIVE;

    private LocalDate contractStartDate;

    private LocalDate contractEndDate;

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