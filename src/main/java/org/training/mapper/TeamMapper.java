package org.training.mapper;

import org.training.dto.team.TeamCreateRequest;
import org.training.dto.team.TeamResponse;
import org.training.model.Team;

import java.util.List;

public interface TeamMapper {

    Team mapToTeam(TeamCreateRequest teamCreateRequest);

    TeamResponse mapToTeamResponse(Team team);

    List<TeamResponse> mapToTeamListResponse(List<Team> teams);

}
