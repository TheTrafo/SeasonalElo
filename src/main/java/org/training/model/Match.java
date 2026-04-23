package org.training.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_home_id")
    private Player playerHome;

    @ManyToOne
    @JoinColumn(name = "player_away_id")
    private Player playerAway;

    private Long scoreHome;

    private Long scoreAway;

    private LocalDate playedAt;

    @ManyToOne
    @JoinColumn(name = "season_id")
    private Season season;

    public Match(Long id, Player playerHome, Player playerAway, Long scoreHome, Long scoreAway, LocalDate playedAt, Season season) {
        this.id = id;
        this.playerHome = playerHome;
        this.playerAway = playerAway;
        this.scoreHome = scoreHome;
        this.scoreAway = scoreAway;
        this.playedAt = playedAt;
        this.season = season;
    }

    public Match(Player playerHome, Player playerAway, Long scoreHome, Long scoreAway, LocalDate playedAt, Season season) {
        this.playerHome = playerHome;
        this.playerAway = playerAway;
        this.scoreHome = scoreHome;
        this.scoreAway = scoreAway;
        this.playedAt = playedAt;
        this.season = season;
    }

}
