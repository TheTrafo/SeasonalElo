package org.training.service;

import org.training.dto.season.SeasonCreateRequest;
import org.training.dto.season.SeasonResponse;
import org.training.dto.season.SeasonUpdateRequest;

import java.util.List;

public interface SeasonService {

    SeasonResponse createSeason(SeasonCreateRequest seasonCreateRequest);

    List<SeasonResponse> getAllSeasons();

    SeasonResponse getSeasonById(Long seasonId);

    SeasonResponse updateSeason(Long id, SeasonUpdateRequest seasonUpdateRequest);

    void deleteSeason(Long id);

    SeasonResponse activateSeason(Long id);

}
