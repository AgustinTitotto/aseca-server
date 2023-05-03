package com.example.asecaserver.repository;

import com.example.asecaserver.model.PlayerStat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerStatRepository extends JpaRepository<PlayerStat, Long> {

    Optional<PlayerStat> findByPlayerId(Long scoringPlayer);
}
