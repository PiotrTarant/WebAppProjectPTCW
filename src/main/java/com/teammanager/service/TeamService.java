package com.teammanager.service;

import com.teammanager.exception.ResourceNotFoundException;
import com.teammanager.model.Team;
import com.teammanager.model.User;
import com.teammanager.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    @Transactional(readOnly = true)
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Team getTeamById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team", "id", id));
    }

    @Transactional(readOnly = true)
    public Team getTeamByName(String name) {
        return teamRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Team", "name", name));
    }

    @Transactional(readOnly = true)
    public List<Team> searchTeams(String query) {
        return teamRepository.findByNameContainingIgnoreCase(query);
    }

    @Transactional
    public Team createTeam(Team team) {
        if (teamRepository.existsByName(team.getName())) {
            throw new IllegalArgumentException("Team name already exists");
        }
        return teamRepository.save(team);
    }

    @Transactional
    public Team updateTeam(Long id, Team teamDetails) {
        Team team = getTeamById(id);
        
        if (!team.getName().equals(teamDetails.getName()) && 
            teamRepository.existsByName(teamDetails.getName())) {
            throw new IllegalArgumentException("Team name already exists");
        }

        team.setName(teamDetails.getName());
        team.setDescription(teamDetails.getDescription());
        team.setCity(teamDetails.getCity());
        team.setHomeVenue(teamDetails.getHomeVenue());
        team.setFoundingDate(teamDetails.getFoundingDate());
        team.setCoach(teamDetails.getCoach());
        team.setOwner(teamDetails.getOwner());
        team.setLogoUrl(teamDetails.getLogoUrl());
        team.setPrimaryColor(teamDetails.getPrimaryColor());
        team.setSecondaryColor(teamDetails.getSecondaryColor());

        return teamRepository.save(team);
    }

    @Transactional
    public void deleteTeam(Long id) {
        Team team = getTeamById(id);
        teamRepository.delete(team);
    }

    @Transactional(readOnly = true)
    public List<Team> getTeamsByCoach(User coach) {
        return teamRepository.findByCoach(coach);
    }

    @Transactional(readOnly = true)
    public List<Team> getTeamsByOwner(User owner) {
        return teamRepository.findByOwner(owner);
    }
} 