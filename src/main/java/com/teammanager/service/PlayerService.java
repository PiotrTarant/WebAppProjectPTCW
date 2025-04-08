package com.teammanager.service;

import com.teammanager.dto.PlayerPrivateDTO;
import com.teammanager.dto.PlayerPublicDTO;
import com.teammanager.model.Player;
import com.teammanager.model.PlayerStatus;
import com.teammanager.model.Team;
import com.teammanager.model.UserRole;
import com.teammanager.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final TeamService teamService;

    @Transactional(readOnly = true)
    public List<PlayerPublicDTO> getAllPlayers() {
        List<Player> players = playerRepository.findAll();
        return players.stream()
                .map(this::convertToPublicDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Object getPlayerDTOById(Long id) {
        Player player = getPlayerById(id);
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        
        if (hasAccessToPrivateInfo(role)) {
            return convertToPrivateDTO(player);
        } else {
            return convertToPublicDTO(player);
        }
    }

    @Transactional(readOnly = true)
    public Player getPlayerById(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found"));
    }

    private boolean hasAccessToPrivateInfo(String role) {
        return role.equals(UserRole.ADMIN.name()) ||
               role.equals(UserRole.TEAM_OWNER.name()) ||
               role.equals(UserRole.GENERAL_MANAGER.name());
    }

    private PlayerPublicDTO convertToPublicDTO(Player player) {
        PlayerPublicDTO dto = new PlayerPublicDTO();
        dto.setId(player.getId());
        dto.setFirstName(player.getFirstName());
        dto.setLastName(player.getLastName());
        dto.setJerseyNumber(player.getJerseyNumber());
        dto.setPosition(player.getPosition());
        dto.setDateOfBirth(player.getDateOfBirth());
        dto.setNationality(player.getNationality());
        dto.setHeight(player.getHeight());
        dto.setWeight(player.getWeight());
        dto.setStatus(player.getStatus().name());
        if (player.getTeam() != null) {
            dto.setTeamId(player.getTeam().getId());
        }
        return dto;
    }

    private PlayerPrivateDTO convertToPrivateDTO(Player player) {
        PlayerPrivateDTO dto = new PlayerPrivateDTO();
        PlayerPublicDTO publicDTO = convertToPublicDTO(player);
        dto.setId(publicDTO.getId());
        dto.setFirstName(publicDTO.getFirstName());
        dto.setLastName(publicDTO.getLastName());
        dto.setJerseyNumber(publicDTO.getJerseyNumber());
        dto.setPosition(publicDTO.getPosition());
        dto.setDateOfBirth(publicDTO.getDateOfBirth());
        dto.setNationality(publicDTO.getNationality());
        dto.setHeight(publicDTO.getHeight());
        dto.setWeight(publicDTO.getWeight());
        dto.setStatus(publicDTO.getStatus());
        dto.setTeamId(publicDTO.getTeamId());
        
        dto.setContractStartDate(player.getContractStartDate());
        dto.setContractEndDate(player.getContractEndDate());
        dto.setContractValue(player.getContractValue());
        dto.setContractFee(player.getContractFee());
        
        return dto;
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