package org.training.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.training.dto.season.SeasonCreateRequest;
import org.training.dto.season.SeasonResponse;
import org.training.dto.season.SeasonUpdateRequest;
import org.training.service.SeasonService;

import java.util.List;

@RestController
@RequestMapping("/seasons")
public class SeasonController {

    private final SeasonService seasonService;

    public SeasonController(SeasonService seasonService) {
        this.seasonService = seasonService;
    }

    @PostMapping
    ResponseEntity<SeasonResponse> createSeason(@RequestBody SeasonCreateRequest newSeason) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(seasonService.createSeason(newSeason));
    }

    @GetMapping
    ResponseEntity<List<SeasonResponse>> allSeasons() {
        return ResponseEntity.ok(seasonService.getAllSeasons());
    }

    @GetMapping("/{id}")
    ResponseEntity<SeasonResponse> seasonById(@PathVariable Long id) {
        return ResponseEntity.ok(seasonService.getSeasonById(id));
    }

    @PutMapping("/{id}")
    ResponseEntity<SeasonResponse> updateSeason(@PathVariable Long id, @RequestBody SeasonUpdateRequest editedSeason) {
        return ResponseEntity.ok(seasonService.updateSeason(id, editedSeason));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteSeason(@PathVariable Long id) {
        seasonService.deleteSeason(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/activate")
    ResponseEntity<SeasonResponse> activateSeason(@PathVariable Long id){
        return ResponseEntity.ok(seasonService.activateSeason(id));
    }

}
