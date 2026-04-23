package org.training.controller;


import org.springframework.web.bind.annotation.*;
import org.training.dto.season.SeasonCreateRequest;
import org.training.dto.season.SeasonResponse;
import org.training.dto.season.SeasonUpdateRequest;

import java.util.List;

@RestController
@RequestMapping("/seasons")
public class SeasonController {

    //TODO: integrate after implementing service layer

    @PostMapping
    SeasonResponse newSeason(@RequestBody SeasonCreateRequest newSeason) {
        return null;
    }

    @GetMapping
    List<SeasonResponse> allSeasons() {
        return null;
    }

    @GetMapping("/{id}")
    SeasonResponse seasonById(@PathVariable Long id) {
        return null;
    }

    @PutMapping("/{id}")
    SeasonResponse updateSeason(@PathVariable Long id, @RequestBody SeasonUpdateRequest editedSeason) {
        return null;
    }

    @DeleteMapping("/{id}")
    void deleteSeason(@PathVariable Long id) {
    }

}
