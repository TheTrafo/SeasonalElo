package org.training.mapper;

import org.training.dto.season.SeasonCreateRequest;
import org.training.dto.season.SeasonResponse;
import org.training.model.Season;

import java.util.List;

public interface SeasonMapper {
    Season mapToSeason(SeasonCreateRequest seasonCreateRequest);

    SeasonResponse mapToSeasonResponse(Season season);

    List<SeasonResponse> mapToSeasonListResponse(List<Season> seasons);

}