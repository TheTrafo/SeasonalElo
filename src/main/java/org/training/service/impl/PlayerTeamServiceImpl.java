package org.training.service.impl;

import org.springframework.stereotype.Service;
import org.training.dto.playerTeamMembership.AddMemberRequest;
import org.training.dto.playerTeamMembership.PlayerTeamResponse;
import org.training.mapper.PlayerTeamMapper;
import org.training.model.Player;
import org.training.model.PlayerTeam;
import org.training.model.Team;
import org.training.repository.PlayerRepository;
import org.training.repository.PlayerTeamRepository;
import org.training.repository.TeamRepository;
import org.training.service.PlayerTeamService;

@Service
public class PlayerTeamServiceImpl implements PlayerTeamService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final PlayerTeamRepository playerTeamRepository;
    private final PlayerTeamMapper playerTeamMapper;

    public PlayerTeamServiceImpl(TeamRepository teamRepository, PlayerRepository playerRepository,
                                 PlayerTeamRepository playerTeamRepository, PlayerTeamMapper playerTeamMapper){
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.playerTeamRepository = playerTeamRepository;
        this.playerTeamMapper = playerTeamMapper;
    }

    @Override
    public PlayerTeamResponse addPlayerToTeam(Long teamId, AddMemberRequest addMemberRequest) {
        Team team = teamRepository.findById(teamId).orElseThrow();
        Player player = playerRepository.findById(addMemberRequest.getPlayerId()).orElseThrow();
        PlayerTeam playerTeam = new PlayerTeam(player, team);
        PlayerTeam saved = playerTeamRepository.save(playerTeam);
        return playerTeamMapper.toPlayerTeamResponse(saved);
    }

    @Override
    public PlayerTeamResponse removePlayerFromTeam(Long teamId, Long playerId) {
        return null;
    }

}
