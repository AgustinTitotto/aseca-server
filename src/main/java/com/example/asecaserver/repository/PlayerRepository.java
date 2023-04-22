package com.example.asecaserver.repository;

import com.example.asecaserver.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
