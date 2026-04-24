package org.training.mapper.impl;

import org.springframework.stereotype.Component;
import org.training.dto.season.SeasonCreateRequest;
import org.training.dto.season.SeasonResponse;
import org.training.mapper.SeasonMapper;
import org.training.model.Season;

import java.util.List;

@Component
public class SeasonMapperImpl implements SeasonMapper {

    @Override
    public Season mapToSeason(SeasonCreateRequest seasonCreateRequest) {
        return new Season(
                seasonCreateRequest.getName(),
                seasonCreateRequest.getStartDate(),
                seasonCreateRequest.getEndDate()
        );
    }

    @Override
    public SeasonResponse mapToSeasonResponse(Season season) {
        return new SeasonResponse(
                season.getId(),
                season.getName(),
                season.getStartDate(),
                season.getEndDate(),
                season.getActive()
        );
    }

    @Override
    public List<SeasonResponse> mapToSeasonListResponse(List<Season> seasons) {
        return seasons.stream()
                .map(this::mapToSeasonResponse)
                .toList();
    }

}
