package org.training.service.impl;

import org.springframework.stereotype.Service;
import org.training.dto.player.PlayerCreateRequest;
import org.training.dto.player.PlayerResponse;
import org.training.dto.player.PlayerUpdateRequest;
import org.training.mapper.PlayerMapper;
import org.training.model.Player;
import org.training.repository.PlayerRepository;
import org.training.service.PlayerService;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    private final PlayerMapper playerMapper;

    public PlayerServiceImpl(PlayerRepository playerRepository, PlayerMapper playerMapper) {
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
    }

    @Override
    public PlayerResponse createPlayer(PlayerCreateRequest playerCreateRequest) {
        Player player = playerRepository.save(playerMapper.mapToPlayer(playerCreateRequest));
        return playerMapper.mapToPlayerResponse(player);
    }

    @Override
    public List<PlayerResponse> getAllPlayers() {
        List<Player> players = playerRepository.findAll();
        return playerMapper.mapToPlayerListResponse(players);
    }

    @Override
    public PlayerResponse getPlayerById(Long playerId) {
        Player player = playerRepository.findById(playerId).orElseThrow();
        return playerMapper.mapToPlayerResponse(player);
    }

    @Override
    public PlayerResponse updatePlayer(Long id, PlayerUpdateRequest playerUpdateRequest) {
        Player playerToUpdate = playerRepository.findById(id).orElseThrow();
        playerToUpdate.setUsername(playerUpdateRequest.getUsername());
        playerToUpdate.setEmail(playerUpdateRequest.getEmail());
        Player saved = playerRepository.save(playerToUpdate);
        return playerMapper.mapToPlayerResponse(saved);
    }

    @Override
    public void deletePlayer(Long id) {
        Player player = playerRepository.findById(id).orElseThrow();
        playerRepository.delete(player);
    }
}
