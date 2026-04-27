package org.training.service;

import org.training.dto.match.MatchCreateRequest;
import org.training.dto.match.MatchResponse;

public interface MatchService {
    MatchResponse createMatch(MatchCreateRequest matchCreateRequest);
}
