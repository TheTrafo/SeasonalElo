package org.training.dto.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.training.dto.player.PlayerResponse;
import org.training.dto.season.SeasonResponse;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class MatchResponse {

    private Long id;

    private PlayerResponse playerHome;

    private PlayerResponse playerAway;

    private Long scoreHome;

    private Long scoreAway;

    private LocalDate playedAt;

    private SeasonResponse season;

}
