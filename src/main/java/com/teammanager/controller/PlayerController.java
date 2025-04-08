package com.teammanager.controller;

import com.teammanager.dto.PlayerDto;
import com.teammanager.dto.PlayerPublicDTO;
import com.teammanager.mapper.PlayerMapper;
import com.teammanager.model.Player;
import com.teammanager.model.PlayerStatus;
import com.teammanager.service.PlayerService;
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
@RequestMapping("/api/players")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;
    private final PlayerMapper playerMapper;

    @GetMapping
    public ResponseEntity<List<PlayerPublicDTO>> getAllPlayers() {
        List<PlayerPublicDTO> players = playerService.getAllPlayers();
        return ResponseEntity.ok(players);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPlayerById(@PathVariable Long id) {
        Object player = playerService.getPlayerDTOById(id);
        return ResponseEntity.ok(player);
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<PlayerDto>> getPlayersByTeam(@PathVariable Long teamId) {
        List<PlayerDto> players = playerService.getPlayersByTeam(teamId).stream()
                .map(playerMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(players);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PlayerDto>> searchPlayers(@RequestParam String query) {
        List<PlayerDto> players = playerService.searchPlayers(query).stream()
                .map(playerMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(players);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH')")
    public ResponseEntity<PlayerDto> createPlayer(@Valid @RequestBody PlayerDto playerDto) {
        Player player = playerMapper.toEntity(playerDto);
        player.setStatus(PlayerStatus.ACTIVE);
        Player result = playerService.createPlayer(player, playerDto.getTeamId());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.getId())
                .toUri();

        return ResponseEntity.created(location).body(playerMapper.toDto(result));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH')")
    public ResponseEntity<PlayerDto> updatePlayer(@PathVariable Long id, @Valid @RequestBody PlayerDto playerDto) {
        Player player = playerMapper.toEntity(playerDto);
        Player result = playerService.updatePlayer(id, player);
        return ResponseEntity.ok(playerMapper.toDto(result));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH')")
    public ResponseEntity<PlayerDto> updatePlayerStatus(
            @PathVariable Long id,
            @RequestParam PlayerStatus status) {
        Player result = playerService.updatePlayerStatus(id, status);
        return ResponseEntity.ok(playerMapper.toDto(result));
    }

    @PostMapping("/{id}/transfer/{newTeamId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlayerDto> transferPlayer(
            @PathVariable Long id,
            @PathVariable Long newTeamId) {
        Player result = playerService.transferPlayer(id, newTeamId);
        return ResponseEntity.ok(playerMapper.toDto(result));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }
} 