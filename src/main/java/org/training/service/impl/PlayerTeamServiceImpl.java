package org.training.service.impl;

import org.springframework.stereotype.Service;
import org.training.dto.player.PlayerResponse;
import org.training.dto.playerTeamMembership.AddMemberRequest;
import org.training.dto.playerTeamMembership.PlayerTeamResponse;
import org.training.exception.PlayerAlreadyInTeamException;
import org.training.exception.PlayerNotPartOfTeam;
import org.training.mapper.PlayerMapper;
import org.training.mapper.PlayerTeamMapper;
import org.training.model.Player;
import org.training.model.PlayerTeam;
import org.training.model.Team;
import org.training.repository.PlayerRepository;
import org.training.repository.PlayerTeamRepository;
import org.training.repository.TeamRepository;
import org.training.service.PlayerTeamService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerTeamServiceImpl implements PlayerTeamService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final PlayerTeamRepository playerTeamRepository;
    private final PlayerTeamMapper playerTeamMapper;
    private final PlayerMapper playerMapper;

    public PlayerTeamServiceImpl(TeamRepository teamRepository, PlayerRepository playerRepository,
                                 PlayerTeamRepository playerTeamRepository, PlayerTeamMapper playerTeamMapper, PlayerMapper playerMapper) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.playerTeamRepository = playerTeamRepository;
        this.playerTeamMapper = playerTeamMapper;
        this.playerMapper = playerMapper;
    }

    @Override
    public PlayerTeamResponse addPlayerToTeam(Long teamId, AddMemberRequest addMemberRequest) {
        Team team = teamRepository.findById(teamId).orElseThrow();
        Player player = playerRepository.findById(addMemberRequest.getPlayerId()).orElseThrow();
        if (playerTeamRepository.findByPlayerIdAndLeftAtIsNull(player.getId()).isEmpty()) {
            PlayerTeam playerTeam = new PlayerTeam(player, team);
            PlayerTeam saved = playerTeamRepository.save(playerTeam);
            return playerTeamMapper.toPlayerTeamResponse(saved);
        } else {
            throw new PlayerAlreadyInTeamException("Player already in team");
        }
    }

    @Override
    public PlayerTeamResponse removePlayerFromTeam(Long teamId, Long playerId) {
        Optional<PlayerTeam> membership = playerTeamRepository.findByPlayerIdAndTeamIdAndLeftAtIsNull(playerId, teamId);
        if (membership.isPresent()) {
            PlayerTeam playerTeam = membership.get();
            playerTeam.setLeftAt(LocalDate.now());
            playerTeamRepository.save(playerTeam);
            return playerTeamMapper.toPlayerTeamResponse(playerTeam);
        } else {
            throw new PlayerNotPartOfTeam("Player was not in the selected team");
        }
    }

    @Override
    public List<PlayerResponse> getAllTeamMembers(Long teamId) {
        List<PlayerTeam> playerTeam = playerTeamRepository.findByTeamIdAndLeftAtIsNull(teamId);
        return playerTeam.stream()
                .map(pt -> playerMapper.mapToPlayerResponse(pt.getPlayer()))
                .toList();
    }

}
