package org.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.training.model.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

}
