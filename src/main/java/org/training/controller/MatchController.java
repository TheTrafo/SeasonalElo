package org.training.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.training.dto.match.MatchCreateRequest;
import org.training.dto.match.MatchResponse;
import org.training.service.MatchService;

@RestController
@RequestMapping("/matches")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping
    ResponseEntity<MatchResponse> matchRecord(@RequestBody MatchCreateRequest matchCreateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(matchService.createMatch(matchCreateRequest));
    }

}
