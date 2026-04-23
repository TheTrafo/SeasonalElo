package org.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.training.model.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
