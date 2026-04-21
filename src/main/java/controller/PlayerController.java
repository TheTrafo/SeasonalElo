package controller;

import model.Player;
import org.springframework.web.bind.annotation.*;
import repository.PlayerRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class PlayerController {

    private final PlayerRepository repository;

    PlayerController(PlayerRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/players")
    Player newPlayer(@RequestBody Player newPlayer){
        return repository.save(newPlayer);
    }

    @GetMapping("/players")
    List<Player> allPlayers(){
        return repository.findAll();
    }

    @GetMapping("/players/{id}")
    Optional<Player> playerById(Long id){
        return repository.findById(id);
    }

    @PutMapping("/players/{id}")
    Player updatePlayer(Long id, @RequestBody Player editedPlayer){
        Optional<Player> playerToUpdate = repository.findById(id);
        if(playerToUpdate.isPresent()) {
            return new Player(
                    editedPlayer.getId(),
                    editedPlayer.getUsername(),
                    editedPlayer.getEmail(),
                    editedPlayer.getEloRating(),
                    editedPlayer.getRegisteredAt()
            );
        } else {
            return null;
        }

    }

}
