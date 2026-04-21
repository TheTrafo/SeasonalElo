package org.training.controller;



import org.springframework.web.bind.annotation.*;
import org.training.model.Season;
import org.training.repository.SeasonRepository;

import java.util.List;

@RestController
public class SeasonController {

    private final SeasonRepository repository;

    SeasonController(SeasonRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/seasons")
    Season newSeason(@RequestBody Season newSeason){
        return repository.save(newSeason);
    }

    @GetMapping("/seasons")
    List<Season> allSeasons(){
        return repository.findAll();
    }

    @GetMapping("/seasons/{id}")
    Season seasonById(@PathVariable Long id){
        return repository.findById(id).orElseThrow();
    }

    @PutMapping("/seasons/{id}")
    Season updateSeason(@PathVariable Long id, @RequestBody Season editedSeason) throws Exception {
        Season season = repository.findById(id).orElseThrow();
        season.setName(editedSeason.getName());
        season.setStartDate(editedSeason.getStartDate());
        season.setEndDate(editedSeason.getEndDate());
        return repository.save(season);
    }

    @DeleteMapping("/seasons/{id}")
    void deleteSeason(@PathVariable Long id){
        Season seasonToDelete = repository.findById(id).orElseThrow();
        repository.delete(seasonToDelete);
    }

}
