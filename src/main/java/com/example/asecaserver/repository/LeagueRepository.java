package com.example.asecaserver.repository;

import com.example.asecaserver.model.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {

}
