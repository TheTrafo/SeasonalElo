package org.training.mapper.impl;

import org.training.dto.match.MatchCreateRequest;
import org.training.dto.match.MatchResponse;
import org.training.mapper.MatchMapper;
import org.training.mapper.PlayerMapper;
import org.training.mapper.SeasonMapper;
import org.training.model.Match;
import org.training.model.Player;

public class MatchMapperImpl implements MatchMapper {

    private final PlayerMapper playerMapper;
    private final SeasonMapper seasonMapper;

    public MatchMapperImpl(PlayerMapper playerMapper, SeasonMapper seasonMapper) {
        this.playerMapper = playerMapper;
        this.seasonMapper = seasonMapper;
    }

    @Override
    public Match mapToMatch(Player home, Player away, MatchCreateRequest matchCreateRequest) {
        return new Match(
                home,
                away,
                matchCreateRequest.getScoreHome(),
                matchCreateRequest.getScoreAway(),
                matchCreateRequest.getPlayedAt()
        );
    }

    @Override
    public MatchResponse mapToMatchResponse(Match match) {
        return new MatchResponse(
                match.getId(),
                playerMapper.mapToPlayerResponse(match.getPlayerHome()),
                playerMapper.mapToPlayerResponse(match.getPlayerAway()),
                match.getScoreHome(),
                match.getScoreAway(),
                match.getPlayedAt(),
                seasonMapper.mapToSeasonResponse(match.getSeason())
        );
    }
}
