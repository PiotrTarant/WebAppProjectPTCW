package com.teammanager.service;

import com.teammanager.exception.ResourceNotFoundException;
import com.teammanager.model.Player;
import com.teammanager.model.PlayerStatus;
import com.teammanager.model.Team;
import com.teammanager.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final TeamService teamService;

    @Transactional(readOnly = true)
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Player getPlayerById(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Player", "id", id));
    }

    @Transactional(readOnly = true)
    public List<Player> getPlayersByTeam(Long teamId) {
        Team team = teamService.getTeamById(teamId);
        return playerRepository.findByTeam(team);
    }

    @Transactional(readOnly = true)
    public List<Player> getPlayersByTeamAndStatus(Long teamId, PlayerStatus status) {
        Team team = teamService.getTeamById(teamId);
        return playerRepository.findByTeamAndStatus(team, status);
    }

    @Transactional(readOnly = true)
    public List<Player> searchPlayers(String searchTerm) {
        return playerRepository.findByFirstNameContainingOrLastNameContaining(searchTerm, searchTerm);
    }

    @Transactional
    public Player createPlayer(Player player, Long teamId) {
        Team team = teamService.getTeamById(teamId);
        player.setTeam(team);
        return playerRepository.save(player);
    }

    @Transactional
    public Player updatePlayer(Long id, Player playerDetails) {
        Player player = getPlayerById(id);

        player.setFirstName(playerDetails.getFirstName());
        player.setLastName(playerDetails.getLastName());
        player.setDateOfBirth(playerDetails.getDateOfBirth());
        player.setNationality(playerDetails.getNationality());
        player.setJerseyNumber(playerDetails.getJerseyNumber());
        player.setPosition(playerDetails.getPosition());
        player.setHeight(playerDetails.getHeight());
        player.setWeight(playerDetails.getWeight());
        player.setBiography(playerDetails.getBiography());
        player.setPhotoUrl(playerDetails.getPhotoUrl());
        player.setStatus(playerDetails.getStatus());
        player.setContractStartDate(playerDetails.getContractStartDate());
        player.setContractEndDate(playerDetails.getContractEndDate());

        return playerRepository.save(player);
    }

    @Transactional
    public Player updatePlayerStatus(Long id, PlayerStatus status) {
        Player player = getPlayerById(id);
        player.setStatus(status);
        return playerRepository.save(player);
    }

    @Transactional
    public void deletePlayer(Long id) {
        Player player = getPlayerById(id);
        playerRepository.delete(player);
    }

    @Transactional
    public Player transferPlayer(Long playerId, Long newTeamId) {
        Player player = getPlayerById(playerId);
        Team newTeam = teamService.getTeamById(newTeamId);
        
        player.setTeam(newTeam);
        player.setStatus(PlayerStatus.ACTIVE);
        
        return playerRepository.save(player);
    }
} 