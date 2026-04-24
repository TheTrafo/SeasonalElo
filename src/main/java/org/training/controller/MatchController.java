package org.training.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.training.dto.match.MatchCreateRequest;
import org.training.dto.match.MatchResponse;

@RestController
@RequestMapping("/matches")
public class MatchController {


    @PostMapping
    ResponseEntity<MatchResponse> matchRecord(MatchCreateRequest matchCreateRequest){
        /// TODO: Implement Match Service and endpoints
        return null;
    }

}
