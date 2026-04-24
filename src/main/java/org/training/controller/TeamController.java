package org.training.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.training.dto.player.PlayerResponse;
import org.training.dto.playerTeamMembership.AddMemberRequest;
import org.training.dto.playerTeamMembership.PlayerTeamResponse;
import org.training.dto.team.TeamCreateRequest;
import org.training.dto.team.TeamResponse;
import org.training.service.PlayerTeamService;
import org.training.service.TeamService;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final PlayerTeamService playerTeamService;

    private final TeamService teamService;

    public TeamController(PlayerTeamService playerTeamService, TeamService teamService) {
        this.playerTeamService = playerTeamService;
        this.teamService = teamService;
    }

    @PostMapping
    ResponseEntity<TeamResponse> createTeam(@RequestBody TeamCreateRequest newTeam) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(teamService.createTeam(newTeam));
    }

    @PostMapping("/{teamId}/members")
    ResponseEntity<PlayerTeamResponse> addPlayerToTeam(@PathVariable Long teamId, @RequestBody AddMemberRequest addMemberRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(playerTeamService.addPlayerToTeam(teamId, addMemberRequest));
    }

    @DeleteMapping("/{teamId}/members/{playerId}")
    ResponseEntity<PlayerTeamResponse> removePlayerFromTeam(@PathVariable Long teamId, @PathVariable Long playerId) {
        return ResponseEntity.ok(playerTeamService.removePlayerFromTeam(teamId, playerId));
    }

    @GetMapping("{teamId}/members")
    ResponseEntity<List<PlayerResponse>> getAllTeamMembers(@PathVariable Long teamId) {
        return ResponseEntity.ok(playerTeamService.getAllTeamMembers(teamId));
    }

}
