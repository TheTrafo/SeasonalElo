package org.training.controller;

import org.springframework.web.bind.annotation.*;
import org.training.dto.player.PlayerCreateRequest;
import org.training.dto.player.PlayerResponse;
import org.training.dto.player.PlayerUpdateRequest;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {

    // TODO: integrate after implementing service layer

    @PostMapping
    PlayerResponse newPlayer(@RequestBody PlayerCreateRequest newPlayer) {
        return null;
    }

    @GetMapping
    List<PlayerResponse> allPlayers() {
        return null;
    }

    @GetMapping("/{id}")
    PlayerResponse playerById(@PathVariable Long id) {
        return null;
    }

    @PutMapping("/{id}")
    PlayerResponse updatePlayer(@PathVariable Long id, @RequestBody PlayerUpdateRequest updatedPlayer) {
        return null;
    }

    @DeleteMapping("/{id}")
    void deletePlayer(@PathVariable Long id) {
    }
}
