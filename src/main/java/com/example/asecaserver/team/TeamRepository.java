package com.example.asecaserver.team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("SELECT s FROM Team s WHERE s.teamName = ?1")
    Team findByTeamName(String teamName);

}
