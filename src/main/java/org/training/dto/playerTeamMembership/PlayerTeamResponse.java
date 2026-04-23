package org.training.dto.playerTeamMembership;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.training.dto.player.PlayerResponse;
import org.training.dto.team.TeamResponse;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PlayerTeamResponse {

    private Long id;

    private PlayerResponse player;

    private TeamResponse team;

    private LocalDate joinedAt;

    private LocalDate leftAt;

}
