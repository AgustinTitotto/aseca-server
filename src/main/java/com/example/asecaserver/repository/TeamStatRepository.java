package com.example.asecaserver.repository;

import com.example.asecaserver.model.TeamStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamStatRepository extends JpaRepository<TeamStat, Long> {
    Optional<TeamStat> findByLeagueIdAndTeamId(Long id, Long id1);
    List<TeamStat> findAllByLeagueId(Long leagueId);
}
