package org.training.service;

import org.training.dto.player.PlayerCreateRequest;
import org.training.dto.player.PlayerResponse;
import org.training.dto.player.PlayerUpdateRequest;

import java.util.List;

public interface PlayerService {

    PlayerResponse createPlayer(PlayerCreateRequest playerCreateRequest);

    List<PlayerResponse> getAllPlayers();

    PlayerResponse getPlayerById(Long playerId);

    PlayerResponse updatePlayer(Long id, PlayerUpdateRequest playerUpdateRequest);

    void deletePlayer(Long id);

}
