package org.training.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.training.dto.match.MatchCreateRequest;
import org.training.dto.match.MatchResponse;
import org.training.enums.MatchResult;
import org.training.exception.PlayerNotFoundException;
import org.training.exception.SamePlayerException;
import org.training.exception.SeasonNotActiveException;
import org.training.mapper.MatchMapper;
import org.training.model.Match;
import org.training.model.Player;
import org.training.model.Season;
import org.training.repository.MatchRepository;
import org.training.repository.PlayerRepository;
import org.training.repository.SeasonRepository;
import org.training.service.MatchService;

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
    @Transactional
    public MatchResponse createMatch(MatchCreateRequest matchCreateRequest) {
        if (matchCreateRequest.getPlayerHomeId().equals(matchCreateRequest.getPlayerAwayId())) {
            throw new SamePlayerException("Zápas nemůže hrát jeden hráč sám proti sobě");
        }
        Player playerHome = playerRepository.findById(matchCreateRequest.getPlayerHomeId()).orElseThrow(()
                -> new PlayerNotFoundException("Hráč nebyl nalezen"));
        Player playerAway = playerRepository.findById(matchCreateRequest.getPlayerAwayId()).orElseThrow(()
                -> new PlayerNotFoundException("Hráč nebyl nalezen"));
        Season activeSeason = seasonRepository.findByActiveIsTrue().orElseThrow(()
                -> new SeasonNotActiveException("Není aktivní sezóna"));
        Double playerHomeRating = playerHome.getEloRating();
        Double playerAwayRating = playerAway.getEloRating();
        Double calculatedHomeRating;
        Double calculatedAwayRating;

        int result = Long.compare(matchCreateRequest.getScoreHome(), matchCreateRequest.getScoreAway());
        if (result == 0) {
            calculatedHomeRating = calculateRating(playerHomeRating, playerAwayRating, MatchResult.DRAW);
            calculatedAwayRating = calculateRating(playerAwayRating, playerHomeRating, MatchResult.DRAW);
        } else if (result > 0) {
            calculatedHomeRating = calculateRating(playerHomeRating, playerAwayRating, MatchResult.WIN);
            calculatedAwayRating = calculateRating(playerAwayRating, playerHomeRating, MatchResult.LOSS);
        } else {
            calculatedHomeRating = calculateRating(playerHomeRating, playerAwayRating, MatchResult.LOSS);
            calculatedAwayRating = calculateRating(playerAwayRating, playerHomeRating, MatchResult.WIN);
        }
        Match match = matchMapper.mapToMatch(playerHome, playerAway, matchCreateRequest);
        match.setSeason(activeSeason);
        playerHome.setEloRating(calculatedHomeRating);
        playerAway.setEloRating(calculatedAwayRating);
        Match saved = matchRepository.save(match);
        return matchMapper.mapToMatchResponse(saved);
    }

    private Double calculateRating(Double playerRating, Double opponentRating, MatchResult result) {
        // S_a = 1 (win), 0.5 (draw), 0 (loss)
        double likelihoodOfWin = 1.0 / (1.0 + Math.pow(10.0, (opponentRating - playerRating) / 400.0));
        double s = switch (result) {
            case WIN -> 1.0;
            case DRAW -> 0.5;
            case LOSS -> 0.0;
        };
        return playerRating + 32.0 * (s - likelihoodOfWin);
    }
}
