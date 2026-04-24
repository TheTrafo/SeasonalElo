package org.training.service;

import org.training.dto.team.TeamCreateRequest;
import org.training.dto.team.TeamResponse;
import org.training.dto.team.TeamUpdateRequest;

import java.util.List;

public interface TeamService {

    TeamResponse createTeam(TeamCreateRequest teamCreateRequest);

    List<TeamResponse> getAllTeams();

    TeamResponse getTeamById(Long teamId);

    TeamResponse updateTeam(Long id, TeamUpdateRequest teamUpdateRequest);

    void deleteTeam(Long id);


}
