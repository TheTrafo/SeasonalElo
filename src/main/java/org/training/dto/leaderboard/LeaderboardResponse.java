package org.training.dto.leaderboard;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.training.dto.player.PlayerLeaderboardResponse;
import org.training.dto.season.SeasonResponse;

import java.util.List;

@NoArgsConstructor
@Data
public class LeaderboardResponse {
    SeasonResponse seasonResponse;

    List<PlayerLeaderboardResponse> players;
}
