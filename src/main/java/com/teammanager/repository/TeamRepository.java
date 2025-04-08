package com.teammanager.repository;

import com.teammanager.model.Team;
import com.teammanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByName(String name);
    List<Team> findByCoach(User coach);
    List<Team> findByOwner(User owner);
    Boolean existsByName(String name);
    List<Team> findByNameContainingIgnoreCase(String name);
} 