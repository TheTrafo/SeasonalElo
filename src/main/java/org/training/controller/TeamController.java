package org.training.controller;

import org.springframework.web.bind.annotation.*;
import org.training.dto.player.PlayerResponse;
import org.training.dto.playerTeamMembership.AddMemberRequest;
import org.training.dto.playerTeamMembership.PlayerTeamResponse;
import org.training.dto.team.TeamCreateRequest;
import org.training.dto.team.TeamResponse;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {

    // TODO: integrate after implementing service layer

    @PostMapping
    TeamResponse newTeam(@RequestBody TeamCreateRequest newTeam) {
        return null;
    }

    @PostMapping("/{teamId}/members")
    PlayerTeamResponse addPlayerToTeam(@PathVariable Long teamId, @RequestBody AddMemberRequest addMemberRequest) {
        return null;
    }

    @DeleteMapping("/{teamId}/members/{playerId}")
    PlayerTeamResponse removePlayerFromTeam(@PathVariable Long teamId, @PathVariable Long playerId) {
        return null;
    }

    @GetMapping("{teamId}/members")
    List<PlayerResponse> getAllTeamMembers(@PathVariable Long teamId) {
        return null;
    }

}
