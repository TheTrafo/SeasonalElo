package org.training.mapper;

import org.training.dto.playerTeamMembership.PlayerTeamResponse;
import org.training.model.PlayerTeam;

public interface PlayerTeamMapper {

    PlayerTeamResponse toPlayerTeamResponse(PlayerTeam playerTeam);

}
