package org.training.service;

import org.training.dto.playerTeamMembership.AddMemberRequest;
import org.training.dto.playerTeamMembership.PlayerTeamResponse;

public interface PlayerTeamService {

    PlayerTeamResponse addPlayerToTeam(Long teamId, AddMemberRequest addMemberRequest);

    PlayerTeamResponse removePlayerFromTeam(Long teamId, Long playerId);

}
