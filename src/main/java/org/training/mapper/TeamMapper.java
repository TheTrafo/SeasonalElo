package org.training.mapper;

import org.training.dto.team.TeamCreateRequest;
import org.training.dto.team.TeamResponse;
import org.training.model.Team;

public interface TeamMapper {

    Team mapToTeam(TeamCreateRequest teamCreateRequest);

    TeamResponse mapToTeamResponse(Team team);

}
