package com.teammanager.repository;

import com.teammanager.model.Player;
import com.teammanager.model.PlayerStatus;
import com.teammanager.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findByTeam(Team team);
    List<Player> findByTeamAndStatus(Team team, PlayerStatus status);
    List<Player> findByStatus(PlayerStatus status);
    List<Player> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);
} 