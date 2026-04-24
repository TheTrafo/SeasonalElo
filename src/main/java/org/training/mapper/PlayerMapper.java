package org.training.mapper;

import org.training.dto.player.PlayerCreateRequest;
import org.training.dto.player.PlayerResponse;
import org.training.model.Player;

import java.util.List;

public interface PlayerMapper {

    Player mapToPlayer(PlayerCreateRequest playerCreateRequest);

    PlayerResponse mapToPlayerResponse(Player player);

    List<PlayerResponse> mapToPlayerListResponse(List<Player> players);
}
