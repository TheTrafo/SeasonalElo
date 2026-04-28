package org.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.training.model.Season;

import java.util.List;

@Repository
public interface SeasonRepository extends JpaRepository<Season, Long> {

    List<Season> findByActiveIsTrue();
}
