package org.training.service;

import org.training.dto.leaderboard.LeaderboardResponse;

public interface LeaderboardService {

    LeaderboardResponse getLeaderboard(Long seasonId);

}
