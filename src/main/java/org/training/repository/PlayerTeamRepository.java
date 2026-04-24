package org.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.training.model.PlayerTeam;

import java.util.List;
import java.util.Optional;

public interface PlayerTeamRepository extends JpaRepository<PlayerTeam, Long> {

    List<PlayerTeam> findByTeamIdAndLeftAtIsNull(Long teamId);

    Optional<PlayerTeam> findByPlayerIdAndLeftAtIsNull(Long playerId);

    Optional<PlayerTeam> findByPlayerIdAndTeamIdAndLeftAtIsNull(Long playerId, Long teamId);

}
