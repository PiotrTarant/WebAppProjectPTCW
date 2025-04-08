package com.teammanager.repository;

import com.teammanager.model.Absence;
import com.teammanager.model.AbsenceStatus;
import com.teammanager.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long> {
    List<Absence> findByPlayer(Player player);
    List<Absence> findByPlayerAndStatus(Player player, AbsenceStatus status);
    List<Absence> findByStatus(AbsenceStatus status);
    
    @Query("SELECT a FROM Absence a WHERE a.startDate >= ?1 AND a.startDate <= ?2")
    List<Absence> findByDateRange(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT a FROM Absence a WHERE a.player = ?1 AND ((a.startDate BETWEEN ?2 AND ?3) OR (a.endDate BETWEEN ?2 AND ?3))")
    List<Absence> findOverlappingAbsences(Player player, LocalDateTime start, LocalDateTime end);
} 