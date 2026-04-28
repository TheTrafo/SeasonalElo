package org.training.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.training.dto.leaderboard.LeaderboardResponse;
import org.training.service.LeaderboardService;

@RestController
@RequestMapping("/leaderboard")
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    public LeaderboardController(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    @GetMapping
    ResponseEntity<LeaderboardResponse> getLeaderboard(@RequestParam Long seasonId){
        return ResponseEntity.ok(leaderboardService.getLeaderboard(seasonId));
    }
}
