package org.training.mapper.impl;

import org.springframework.stereotype.Component;
import org.training.dto.player.PlayerCreateRequest;
import org.training.dto.player.PlayerResponse;
import org.training.mapper.PlayerMapper;
import org.training.model.Player;

@Component
public class PlayerMapperImpl implements PlayerMapper {

    @Override
    public Player mapToPlayer(PlayerCreateRequest playerCreateRequest) {
        return new Player(
                playerCreateRequest.getUsername(),
                playerCreateRequest.getEmail()
        );
    }

    @Override
    public PlayerResponse mapToPlayerResponse(Player player) {
        return new PlayerResponse(
                player.getId(),
                player.getUsername(),
                player.getEmail(),
                player.getEloRating(),
                player.getRegisteredAt()
        );
    }
}
