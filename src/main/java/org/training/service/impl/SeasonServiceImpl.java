package org.training.service.impl;

import org.springframework.stereotype.Service;
import org.training.dto.season.SeasonCreateRequest;
import org.training.dto.season.SeasonResponse;
import org.training.dto.season.SeasonUpdateRequest;
import org.training.mapper.SeasonMapper;
import org.training.model.Season;
import org.training.repository.SeasonRepository;
import org.training.service.SeasonService;

import java.time.LocalDate;
import java.util.List;

@Service
public class SeasonServiceImpl implements SeasonService {

    private final SeasonRepository seasonRepository;

    private final SeasonMapper seasonMapper;

    public SeasonServiceImpl(SeasonRepository seasonRepository, SeasonMapper seasonMapper) {
        this.seasonRepository = seasonRepository;
        this.seasonMapper = seasonMapper;
    }

    @Override
    public SeasonResponse createSeason(SeasonCreateRequest seasonCreateRequest) {
        Season season = seasonMapper.mapToSeason(seasonCreateRequest);
        boolean active = seasonRepository.findByActiveIsTrue().isPresent();
        if (seasonCreateRequest.getStartDate().isBefore(LocalDate.now()) &&
                seasonCreateRequest.getEndDate().isAfter(LocalDate.now()) && !active) {
            season.setActive(true);
        }
        Season saved = seasonRepository.save(season);
        return seasonMapper.mapToSeasonResponse(saved);
    }

    @Override
    public List<SeasonResponse> getAllSeasons() {
        List<Season> seasons = seasonRepository.findAll();
        return seasonMapper.mapToSeasonListResponse(seasons);
    }

    @Override
    public SeasonResponse getSeasonById(Long seasonId) {
        Season season = seasonRepository.findById(seasonId).orElseThrow();
        return seasonMapper.mapToSeasonResponse(season);
    }

    @Override
    public SeasonResponse updateSeason(Long id, SeasonUpdateRequest seasonUpdateRequest) {
        Season seasonToUpdate = seasonRepository.findById(id).orElseThrow();
        seasonToUpdate.setName(seasonUpdateRequest.getName());
        seasonToUpdate.setStartDate(seasonUpdateRequest.getStartDate());
        seasonToUpdate.setEndDate(seasonUpdateRequest.getEndDate());
        Season saved = seasonRepository.save(seasonToUpdate);
        return seasonMapper.mapToSeasonResponse(saved);
    }

    @Override
    public void deleteSeason(Long id) {
        Season season = seasonRepository.findById(id).orElseThrow();
        seasonRepository.delete(season);
    }
}
