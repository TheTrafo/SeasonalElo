package org.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.training.model.Match;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {

    List<Match> findBySeasonId(Long seasonId);

}
