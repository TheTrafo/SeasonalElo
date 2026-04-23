package org.training.dto.match;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MatchCreateRequest {

    private Long playerHomeId;

    private Long playerAwayId;

    private Long scoreHome;

    private Long scoreAway;

    private LocalDate playedAt;

    private Long seasonId;

}
