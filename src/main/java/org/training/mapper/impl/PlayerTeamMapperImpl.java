package org.training.mapper.impl;

import org.springframework.stereotype.Component;

import org.training.dto.playerTeamMembership.PlayerTeamResponse;
import org.training.mapper.PlayerMapper;
import org.training.mapper.PlayerTeamMapper;
import org.training.mapper.TeamMapper;
import org.training.model.PlayerTeam;

@Component
public class PlayerTeamMapperImpl implements PlayerTeamMapper {

    private final PlayerMapper playerMapper;
    private final TeamMapper teamMapper;

    public PlayerTeamMapperImpl(PlayerMapper playerMapper, TeamMapper teamMapper) {
        this.playerMapper = playerMapper;
        this.teamMapper = teamMapper;
    }
    @Override
    public PlayerTeamResponse toPlayerTeamResponse(PlayerTeam playerTeam) {
        return new PlayerTeamResponse(
                playerTeam.getId(),
                playerMapper.mapToPlayerResponse(playerTeam.getPlayer()),
                teamMapper.mapToTeamResponse(playerTeam.getTeam()),
                playerTeam.getJoinedAt(),
                playerTeam.getLeftAt()
        );
    }
}
