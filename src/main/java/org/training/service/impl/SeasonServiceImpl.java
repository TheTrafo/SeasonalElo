package org.training.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.training.dto.season.SeasonCreateRequest;
import org.training.dto.season.SeasonResponse;
import org.training.dto.season.SeasonUpdateRequest;
import org.training.exception.SeasonNotFoundException;
import org.training.mapper.SeasonMapper;
import org.training.model.Player;
import org.training.model.Season;
import org.training.repository.PlayerRepository;
import org.training.repository.SeasonRepository;
import org.training.service.SeasonService;

import java.time.LocalDate;
import java.util.List;

@Service
public class SeasonServiceImpl implements SeasonService {

    private final SeasonRepository seasonRepository;
    private final SeasonMapper seasonMapper;
    private final PlayerRepository playerRepository;

    public SeasonServiceImpl(SeasonRepository seasonRepository, SeasonMapper seasonMapper, PlayerRepository playerRepository) {
        this.seasonRepository = seasonRepository;
        this.seasonMapper = seasonMapper;
        this.playerRepository = playerRepository;
    }

    @Override
    public SeasonResponse createSeason(SeasonCreateRequest seasonCreateRequest) {
        Season season = seasonMapper.mapToSeason(seasonCreateRequest);
        boolean active = !seasonRepository.findByActiveIsTrue().isEmpty();
        season.setActive(!season.getStartDate().isAfter(LocalDate.now()) &&
                !season.getEndDate().isBefore(LocalDate.now()) && !active);
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
        Season season = seasonRepository.findById(seasonId).orElseThrow(()
                -> new SeasonNotFoundException("Sezóna nebyla nalezena"));
        return seasonMapper.mapToSeasonResponse(season);
    }

    @Override
    public SeasonResponse updateSeason(Long id, SeasonUpdateRequest seasonUpdateRequest) {
        Season seasonToUpdate = seasonRepository.findById(id).orElseThrow(()
                -> new SeasonNotFoundException("Sezóna nebyla nalezena"));
        seasonToUpdate.setName(seasonUpdateRequest.getName());
        seasonToUpdate.setStartDate(seasonUpdateRequest.getStartDate());
        seasonToUpdate.setEndDate(seasonUpdateRequest.getEndDate());
        Season saved = seasonRepository.save(seasonToUpdate);
        return seasonMapper.mapToSeasonResponse(saved);
    }

    @Override
    public void deleteSeason(Long id) {
        Season season = seasonRepository.findById(id).orElseThrow(()
                -> new SeasonNotFoundException("Sezóna nebyla nalezena"));
        seasonRepository.delete(season);
    }

    @Override
    @Transactional
    public SeasonResponse activateSeason(Long id) {
        Season season = seasonRepository.findById(id).orElseThrow(()
                -> new SeasonNotFoundException("Sezóna nebyla nalezena"));
        List<Player> players = playerRepository.findAll();

        if (seasonRepository.findByActiveIsTrue().isEmpty()){
            season.setActive(true);
        } else {
            List<Season> activeSeason = seasonRepository.findByActiveIsTrue();
            activeSeason.forEach(s -> s.setActive(false));
            season.setActive(true);
        }
        players.forEach(player -> player.setEloRating(1000.0));
        return seasonMapper.mapToSeasonResponse(season);
    }
}
