package com.teammanager.controller;

import com.teammanager.dto.TeamDto;
import com.teammanager.mapper.TeamMapper;
import com.teammanager.model.Team;
import com.teammanager.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;
    private final TeamMapper teamMapper;

    @GetMapping
    public ResponseEntity<List<TeamDto>> getAllTeams() {
        List<TeamDto> teams = teamService.getAllTeams().stream()
                .map(teamMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDto> getTeamById(@PathVariable Long id) {
        Team team = teamService.getTeamById(id);
        return ResponseEntity.ok(teamMapper.toDto(team));
    }

    @GetMapping("/search")
    public ResponseEntity<List<TeamDto>> searchTeams(@RequestParam String query) {
        List<TeamDto> teams = teamService.searchTeams(query).stream()
                .map(teamMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(teams);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeamDto> createTeam(@Valid @RequestBody TeamDto teamDto) {
        Team team = teamMapper.toEntity(teamDto);
        Team result = teamService.createTeam(team);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.getId())
                .toUri();

        return ResponseEntity.created(location).body(teamMapper.toDto(result));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH')")
    public ResponseEntity<TeamDto> updateTeam(@PathVariable Long id, @Valid @RequestBody TeamDto teamDto) {
        Team team = teamMapper.toEntity(teamDto);
        Team result = teamService.updateTeam(id, team);
        return ResponseEntity.ok(teamMapper.toDto(result));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/players")
    public ResponseEntity<List<TeamDto>> getTeamPlayers(@PathVariable Long id) {
        List<TeamDto> players = teamService.getTeamPlayers(id).stream()
                .map(teamMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(players);
    }

    @PostMapping("/{id}/players")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH')")
    public ResponseEntity<TeamDto> addPlayerToTeam(
            @PathVariable Long id,
            @RequestParam Long playerId) {
        Team team = teamService.addPlayerToTeam(id, playerId);
        return ResponseEntity.ok(teamMapper.toDto(team));
    }

    @DeleteMapping("/{id}/players/{playerId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH')")
    public ResponseEntity<TeamDto> removePlayerFromTeam(
            @PathVariable Long id,
            @PathVariable Long playerId) {
        Team team = teamService.removePlayerFromTeam(id, playerId);
        return ResponseEntity.ok(teamMapper.toDto(team));
    }

    @GetMapping("/{id}/statistics")
    public ResponseEntity<TeamDto> getTeamStatistics(@PathVariable Long id) {
        Team team = teamService.getTeamStatistics(id);
        return ResponseEntity.ok(teamMapper.toDto(team));
    }

    @PutMapping("/{id}/coach")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeamDto> assignCoach(
            @PathVariable Long id,
            @RequestParam Long coachId) {
        Team team = teamService.assignCoach(id, coachId);
        return ResponseEntity.ok(teamMapper.toDto(team));
    }

    @DeleteMapping("/{id}/coach")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeamDto> removeCoach(@PathVariable Long id) {
        Team team = teamService.removeCoach(id);
        return ResponseEntity.ok(teamMapper.toDto(team));
    }
} 