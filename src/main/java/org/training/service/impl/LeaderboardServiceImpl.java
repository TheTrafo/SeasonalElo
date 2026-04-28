package org.training.service.impl;

import org.springframework.stereotype.Service;
import org.training.dto.leaderboard.LeaderboardResponse;
import org.training.dto.player.PlayerLeaderboardResponse;
import org.training.exception.SeasonNotFoundException;
import org.training.mapper.SeasonMapper;
import org.training.model.Match;
import org.training.model.Player;
import org.training.model.Season;
import org.training.repository.MatchRepository;
import org.training.repository.SeasonRepository;
import org.training.service.LeaderboardService;

import java.util.*;

@Service
public class LeaderboardServiceImpl implements LeaderboardService {

    private final SeasonRepository seasonRepository;
    private final MatchRepository matchRepository;
    private final SeasonMapper seasonMapper;

    public LeaderboardServiceImpl(SeasonRepository seasonRepository, MatchRepository matchRepository, SeasonMapper seasonMapper) {
        this.seasonRepository = seasonRepository;
        this.matchRepository = matchRepository;
        this.seasonMapper = seasonMapper;
    }

    @Override
    public LeaderboardResponse getLeaderboard(Long seasonId) {
        Season season = seasonRepository.findById(seasonId).orElseThrow(()
                -> new SeasonNotFoundException("Sezóna nenalezena"));
        List<Match> matches = matchRepository.findBySeasonId(seasonId);

        Map<Long, Integer> wins = new HashMap<>();
        Map<Long, Integer> losses = new HashMap<>();
        Map<Long, Integer> draws = new HashMap<>();

        matches.forEach(m -> {
            int result = Long.compare(m.getScoreHome(), m.getScoreAway());
            if (result > 0) {
                wins.merge(m.getPlayerHome().getId(), 1, Integer::sum);
                losses.merge(m.getPlayerAway().getId(), 1, Integer::sum);
            } else if (result < 0) {
                losses.merge(m.getPlayerHome().getId(), 1, Integer::sum);
                wins.merge(m.getPlayerAway().getId(), 1, Integer::sum);
            } else {
                draws.merge(m.getPlayerHome().getId(), 1, Integer::sum);
                draws.merge(m.getPlayerAway().getId(), 1, Integer::sum);
            }
        });

        Set<Player> players = new HashSet<>();
        matches.forEach(m -> {
            players.add(m.getPlayerHome());
            players.add(m.getPlayerAway());
        });


        LeaderboardResponse response = new LeaderboardResponse();
        response.setSeasonResponse(seasonMapper.mapToSeasonResponse(season));
        response.setPlayers(players.stream()
                .map(p -> new PlayerLeaderboardResponse(
                        p.getId(),
                        p.getUsername(),
                        p.getEmail(),
                        p.getEloRating(),
                        p.getRegisteredAt(),
                        wins.getOrDefault(p.getId(), 0)
                                + losses.getOrDefault(p.getId(), 0)
                                + draws.getOrDefault(p.getId(), 0),
                        wins.getOrDefault(p.getId(), 0),
                        losses.getOrDefault(p.getId(), 0),
                        draws.getOrDefault(p.getId(), 0)
                ))
                .sorted(Comparator.comparingDouble(PlayerLeaderboardResponse::getEloRating).reversed())
                .toList());

        return response;
    }
}
