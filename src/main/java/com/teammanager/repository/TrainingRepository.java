package com.teammanager.repository;

import com.teammanager.model.Team;
import com.teammanager.model.Training;
import com.teammanager.model.TrainingStatus;
import com.teammanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
    List<Training> findByTeam(Team team);
    List<Training> findByTeamAndStatus(Team team, TrainingStatus status);
    List<Training> findByCoach(User coach);
    List<Training> findByStatus(TrainingStatus status);
    
    @Query("SELECT t FROM Training t WHERE t.startTime >= ?1 AND t.startTime <= ?2")
    List<Training> findByDateRange(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT t FROM Training t WHERE t.team = ?1 AND t.startTime >= ?2")
    List<Training> findUpcomingTrainingsByTeam(Team team, LocalDateTime from);
} 