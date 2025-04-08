package com.teammanager.mapper;

import com.teammanager.dto.TeamDto;
import com.teammanager.model.Team;
import org.springframework.stereotype.Component;

@Component
public class TeamMapper {
    
    public TeamDto toDto(Team team) {
        if (team == null) {
            return null;
        }

        TeamDto dto = new TeamDto();
        dto.setId(team.getId());
        dto.setName(team.getName());
        dto.setDescription(team.getDescription());
        dto.setCity(team.getCity());
        dto.setHomeVenue(team.getHomeVenue());
        dto.setFoundingDate(team.getFoundingDate());
        dto.setCoachId(team.getCoach() != null ? team.getCoach().getId() : null);
        dto.setOwnerId(team.getOwner() != null ? team.getOwner().getId() : null);
        dto.setLogoUrl(team.getLogoUrl());
        dto.setPrimaryColor(team.getPrimaryColor());
        dto.setSecondaryColor(team.getSecondaryColor());
        
        return dto;
    }

    public Team toEntity(TeamDto dto) {
        if (dto == null) {
            return null;
        }

        Team team = new Team();
        team.setName(dto.getName());
        team.setDescription(dto.getDescription());
        team.setCity(dto.getCity());
        team.setHomeVenue(dto.getHomeVenue());
        team.setFoundingDate(dto.getFoundingDate());
        team.setLogoUrl(dto.getLogoUrl());
        team.setPrimaryColor(dto.getPrimaryColor());
        team.setSecondaryColor(dto.getSecondaryColor());
        
        return team;
    }

    public void updateEntityFromDto(TeamDto dto, Team team) {
        if (dto == null || team == null) {
            return;
        }

        team.setName(dto.getName());
        team.setDescription(dto.getDescription());
        team.setCity(dto.getCity());
        team.setHomeVenue(dto.getHomeVenue());
        team.setFoundingDate(dto.getFoundingDate());
        team.setLogoUrl(dto.getLogoUrl());
        team.setPrimaryColor(dto.getPrimaryColor());
        team.setSecondaryColor(dto.getSecondaryColor());
    }
} 