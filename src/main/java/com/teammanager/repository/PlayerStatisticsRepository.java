package com.teammanager.repository;

import com.teammanager.model.Match;
import com.teammanager.model.Player;
import com.teammanager.model.PlayerStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerStatisticsRepository extends JpaRepository<PlayerStatistics, Long> {
    List<PlayerStatistics> findByPlayer(Player player);
    List<PlayerStatistics> findByMatch(Match match);
    Optional<PlayerStatistics> findByPlayerAndMatch(Player player, Match match);
    
    @Query("SELECT ps FROM PlayerStatistics ps WHERE ps.player = ?1 ORDER BY ps.match.matchDateTime DESC")
    List<PlayerStatistics> findPlayerStatisticsByPlayerOrderByMatchDateDesc(Player player);
    
    @Query("SELECT AVG(ps.fieldGoalsMade) FROM PlayerStatistics ps WHERE ps.player = ?1")
    Double findAverageGoalsForPlayer(Player player);
    
    @Query("SELECT AVG(ps.assists) FROM PlayerStatistics ps WHERE ps.player = ?1")
    Double findAverageAssistsForPlayer(Player player);
} 