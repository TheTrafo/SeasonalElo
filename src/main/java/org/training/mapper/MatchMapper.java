package org.training.mapper;

import org.training.dto.match.MatchCreateRequest;
import org.training.dto.match.MatchResponse;
import org.training.model.Match;
import org.training.model.Player;

public interface MatchMapper {

    Match mapToMatch(Player home, Player away, MatchCreateRequest matchCreateRequest);

    MatchResponse mapToMatchResponse(Match match);

}
