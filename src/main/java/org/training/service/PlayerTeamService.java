package org.training.service;

import org.training.dto.player.PlayerResponse;
import org.training.dto.playerTeamMembership.AddMemberRequest;
import org.training.dto.playerTeamMembership.PlayerTeamResponse;

import java.util.List;

public interface PlayerTeamService {

    PlayerTeamResponse addPlayerToTeam(Long teamId, AddMemberRequest addMemberRequest);

    PlayerTeamResponse removePlayerFromTeam(Long teamId, Long playerId);

    List<PlayerResponse> getAllTeamMembers(Long teamId);

}
