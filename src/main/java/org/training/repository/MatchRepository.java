package org.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.training.model.Match;

public interface MatchRepository extends JpaRepository<Match, Long> {
}
