package com.teammanager.mapper;

import com.teammanager.dto.PlayerDto;
import com.teammanager.model.Player;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper {
    
    public PlayerDto toDto(Player player) {
        if (player == null) {
            return null;
        }

        PlayerDto dto = new PlayerDto();
        dto.setId(player.getId());
        dto.setFirstName(player.getFirstName());
        dto.setLastName(player.getLastName());
        dto.setDateOfBirth(player.getDateOfBirth());
        dto.setNationality(player.getNationality());
        dto.setJerseyNumber(player.getJerseyNumber());
        dto.setPosition(player.getPosition());
        dto.setHeight(player.getHeight());
        dto.setWeight(player.getWeight());
        dto.setBiography(player.getBiography());
        dto.setTeamId(player.getTeam() != null ? player.getTeam().getId() : null);
        dto.setPhotoUrl(player.getPhotoUrl());
        dto.setStatus(player.getStatus());
        dto.setContractStartDate(player.getContractStartDate());
        dto.setContractEndDate(player.getContractEndDate());
        
        return dto;
    }

    public Player toEntity(PlayerDto dto) {
        if (dto == null) {
            return null;
        }

        Player player = new Player();
        player.setFirstName(dto.getFirstName());
        player.setLastName(dto.getLastName());
        player.setDateOfBirth(dto.getDateOfBirth());
        player.setNationality(dto.getNationality());
        player.setJerseyNumber(dto.getJerseyNumber());
        player.setPosition(dto.getPosition());
        player.setHeight(dto.getHeight());
        player.setWeight(dto.getWeight());
        player.setBiography(dto.getBiography());
        player.setPhotoUrl(dto.getPhotoUrl());
        player.setStatus(dto.getStatus());
        player.setContractStartDate(dto.getContractStartDate());
        player.setContractEndDate(dto.getContractEndDate());
        
        return player;
    }

    public void updateEntityFromDto(PlayerDto dto, Player player) {
        if (dto == null || player == null) {
            return;
        }

        player.setFirstName(dto.getFirstName());
        player.setLastName(dto.getLastName());
        player.setDateOfBirth(dto.getDateOfBirth());
        player.setNationality(dto.getNationality());
        player.setJerseyNumber(dto.getJerseyNumber());
        player.setPosition(dto.getPosition());
        player.setHeight(dto.getHeight());
        player.setWeight(dto.getWeight());
        player.setBiography(dto.getBiography());
        player.setPhotoUrl(dto.getPhotoUrl());
        player.setStatus(dto.getStatus());
        player.setContractStartDate(dto.getContractStartDate());
        player.setContractEndDate(dto.getContractEndDate());
    }
} 