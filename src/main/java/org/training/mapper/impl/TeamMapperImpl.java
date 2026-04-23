package org.training.mapper.impl;

import org.training.dto.team.TeamCreateRequest;
import org.training.dto.team.TeamResponse;
import org.training.mapper.TeamMapper;
import org.training.model.Team;

public class TeamMapperImpl implements TeamMapper {

    @Override
    public Team mapToTeam(TeamCreateRequest teamCreateRequest) {
        return new Team(
                teamCreateRequest.getName(),
                teamCreateRequest.getAbbreviation()
        );
    }

    @Override
    public TeamResponse mapToTeamResponse(Team team) {
        return new TeamResponse(
                team.getId(),
                team.getName(),
                team.getAbbreviation(),
                team.getFoundedAt()
        );
    }
}
