package org.training.controller;

import org.springframework.web.bind.annotation.*;
import org.training.model.Player;
import org.training.repository.PlayerRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class PlayerController {

    private final PlayerRepository repository;

    PlayerController(PlayerRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/players")
    Player newPlayer(@RequestBody Player newPlayer) {
        return repository.save(newPlayer);
    }

    @GetMapping("/players")
    List<Player> allPlayers() {
        return repository.findAll();
    }

    @GetMapping("/players/{id}")
    Player playerById(@PathVariable Long id) {
        return repository.findById(id).orElseThrow();
    }

    @PutMapping("/players/{id}")
    Player updatePlayer(@PathVariable Long id, @RequestBody Player editedPlayer) throws Exception {
        Player player = repository.findById(id).orElseThrow();
        player.setUsername(editedPlayer.getUsername());
        player.setEmail(editedPlayer.getEmail());
        player.setEloRating(editedPlayer.getEloRating());
        return repository.save(player);
    }

    @DeleteMapping("/players/{id}")
    void deletePlayer(@PathVariable Long id) {
        Player playerToDelete = repository.findById(id).orElseThrow();
        repository.delete(playerToDelete);
    }
}
