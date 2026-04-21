package controller;


import model.Season;
import org.springframework.web.bind.annotation.*;
import repository.SeasonRepository;

import java.util.List;
import java.util.Optional;

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
    Optional<Season> seasonById(Long id){
        return repository.findById(id);
    }

    @PutMapping("/seasons/{id}")
    Season updateSeason(Long id, @RequestBody Season editedSeason){
        Optional<Season> SeasonToUpdate = repository.findById(id);
        if(SeasonToUpdate.isPresent()) {
            return new Season(
                    editedSeason.getId(),
                    editedSeason.getName(),
                    editedSeason.getStartDate(),
                    editedSeason.getEndDate(),
                    editedSeason.getActive()
            );
        } else {
            return null;
        }

    }
}
