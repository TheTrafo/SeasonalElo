package org.training.service.impl;

import org.springframework.stereotype.Service;
import org.training.dto.match.MatchCreateRequest;
import org.training.dto.match.MatchResponse;
import org.training.enums.MatchResult;
import org.training.exception.SamePlayerException;
import org.training.mapper.MatchMapper;
import org.training.model.Match;
import org.training.model.Player;
import org.training.model.Season;
import org.training.repository.MatchRepository;
import org.training.repository.PlayerRepository;
import org.training.repository.SeasonRepository;
import org.training.service.MatchService;

import java.util.Objects;

@Service
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;
    private final SeasonRepository seasonRepository;
    private final PlayerRepository playerRepository;
    private final MatchMapper matchMapper;

    public MatchServiceImpl(MatchRepository matchRepository, SeasonRepository seasonRepository, PlayerRepository playerRepository, MatchMapper matchMapper) {
        this.matchRepository = matchRepository;
        this.seasonRepository = seasonRepository;
        this.playerRepository = playerRepository;
        this.matchMapper = matchMapper;
    }

    @Override
    public MatchResponse createMatch(MatchCreateRequest matchCreateRequest) {
        Player playerHome = playerRepository.findById(matchCreateRequest.getPlayerHomeId()).orElseThrow();
        Player playerAway = playerRepository.findById(matchCreateRequest.getPlayerAwayId()).orElseThrow();
        Season activeSeason = seasonRepository.findByActiveIsTrue().orElseThrow();

        if (!Objects.equals(playerHome.getId(), playerAway.getId())) {
            int result = Long.compare(matchCreateRequest.getScoreHome(), matchCreateRequest.getScoreAway());
            if (result == 0) {
                calculateRating(playerHome, playerAway, MatchResult.DRAW);
                calculateRating(playerAway, playerHome, MatchResult.DRAW);
            } else if (result > 0) {
                calculateRating(playerHome, playerAway, MatchResult.WIN);
                calculateRating(playerAway, playerHome, MatchResult.LOSS);
            } else {
                calculateRating(playerHome, playerAway, MatchResult.LOSS);
                calculateRating(playerAway, playerHome, MatchResult.WIN);
            }
            Match match = matchMapper.mapToMatch(playerHome, playerAway, matchCreateRequest);
            match.setSeason(activeSeason);
            Match saved = matchRepository.save(match);
            return matchMapper.mapToMatchResponse(saved);
        } else {
            throw new SamePlayerException("Zápas nemůže hrát jeden hráč sám proti sobě");
        }
    }

    private void calculateRating(Player player, Player opponent, MatchResult result) {
        // S_a = 1 (win), 0.5 (draw), 0 (loss)
        double likelihoodOfWin = 1.0 / (1.0 + Math.pow(10, (opponent.getEloRating() - player.getEloRating()) / 400.0));
        switch (result) {
            case WIN -> player.setEloRating(player.getEloRating() + 32.0 * (1.0 - likelihoodOfWin));
            case DRAW -> player.setEloRating(player.getEloRating() + 32.0 * (0.5 - likelihoodOfWin));
            case LOSS -> player.setEloRating(player.getEloRating() + 32.0 * (0.0 - likelihoodOfWin));
        }
    }
}
