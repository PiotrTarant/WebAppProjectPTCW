package com.teammanager.repository;

import com.teammanager.model.AttendanceStatus;
import com.teammanager.model.Player;
import com.teammanager.model.Training;
import com.teammanager.model.TrainingAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingAttendanceRepository extends JpaRepository<TrainingAttendance, Long> {
    List<TrainingAttendance> findByTraining(Training training);
    List<TrainingAttendance> findByPlayer(Player player);
    Optional<TrainingAttendance> findByPlayerAndTraining(Player player, Training training);
    List<TrainingAttendance> findByPlayerAndStatus(Player player, AttendanceStatus status);
    
    @Query("SELECT COUNT(ta) FROM TrainingAttendance ta WHERE ta.player = ?1 AND ta.status = ?2 AND ta.training.startTime BETWEEN ?3 AND ?4")
    Long countAttendancesByPlayerAndStatusBetweenDates(Player player, AttendanceStatus status, LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT ta FROM TrainingAttendance ta WHERE ta.player = ?1 ORDER BY ta.training.startTime DESC")
    List<TrainingAttendance> findPlayerAttendanceHistoryOrderByDateDesc(Player player);
} 