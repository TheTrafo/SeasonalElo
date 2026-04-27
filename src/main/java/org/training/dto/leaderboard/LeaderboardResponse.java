package org.training.dto.leaderboard;

import org.training.dto.player.PlayerLeaderboardResponse;
import org.training.dto.season.SeasonResponse;

import java.util.List;

public class LeaderboardResponse {
    SeasonResponse seasonResponse;

    List<PlayerLeaderboardResponse> players;
}
