package com.example.asecaserver.repository;

import com.example.asecaserver.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END FROM Match s WHERE s.localTeam.id = ?1 AND s.awayTeam.id = ?2")
    Boolean matchExists(Long localTeamId, Long awayTeamId);
}
