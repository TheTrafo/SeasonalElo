package org.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.training.model.PlayerTeam;

import java.util.List;

public interface PlayerTeamRepository extends JpaRepository<PlayerTeam, Long> {

    List<PlayerTeam> findByTeamId(Long teamId);

}
