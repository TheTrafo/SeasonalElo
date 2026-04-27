package org.training.dto.player;

import java.time.LocalDate;

public class PlayerLeaderboardResponse {
    private Long id;

    private String username;

    private String email;

    private Double eloRating;

    private LocalDate registeredAt;

    private Integer totalMatches;

    private Integer wins;

    private Integer losses;

    private Integer draws;
}
