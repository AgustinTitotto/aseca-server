package com.example.asecaserver.team;

import com.example.asecaserver.league.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("SELECT s FROM Team s WHERE s.teamName = ?1")
    Team findByTeamName(String teamName);

    Boolean teamIsInLeague(Team team, League league);
}
