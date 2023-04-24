package com.example.asecaserver.repository;

import com.example.asecaserver.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    Optional<Match> findByLocalTeamIdAndAwayTeamIdAndLeagueId(Long localTeam, Long awayTeam, Long league);
}
