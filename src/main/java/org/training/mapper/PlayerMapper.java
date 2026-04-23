package org.training.mapper;

import org.training.dto.player.PlayerCreateRequest;
import org.training.dto.player.PlayerResponse;
import org.training.model.Player;

public interface PlayerMapper {

    Player mapToPlayer(PlayerCreateRequest playerCreateRequest);

    PlayerResponse mapToPlayerResponse(Player player);
}
