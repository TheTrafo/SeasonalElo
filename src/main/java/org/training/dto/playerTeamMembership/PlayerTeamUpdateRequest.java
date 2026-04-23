package org.training.dto.playerTeamMembership;

import lombok.Data;

@Data
public class PlayerTeamUpdateRequest {

    private Long playerId;

    private Long teamId;

}
