package org.training.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.training.dto.player.PlayerCreateRequest;
import org.training.dto.player.PlayerResponse;
import org.training.dto.player.PlayerUpdateRequest;
import org.training.service.PlayerService;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping
    ResponseEntity<PlayerResponse> createPlayer(@RequestBody PlayerCreateRequest newPlayer) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(playerService.createPlayer(newPlayer));
    }

    @GetMapping
    ResponseEntity<List<PlayerResponse>> allPlayers() {
        return ResponseEntity.ok(playerService.getAllPlayers());
    }

    @GetMapping("/{id}")
    ResponseEntity<PlayerResponse> playerById(@PathVariable Long id) {
        return ResponseEntity.ok(playerService.getPlayerById(id));
    }

    @PutMapping("/{id}")
    ResponseEntity<PlayerResponse> updatePlayer(@PathVariable Long id, @RequestBody PlayerUpdateRequest updatedPlayer) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(playerService.updatePlayer(id, updatedPlayer));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }
}
