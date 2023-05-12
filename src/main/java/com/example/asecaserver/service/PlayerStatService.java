package com.example.asecaserver.service;

import com.example.asecaserver.model.League;
import com.example.asecaserver.model.PlayerStat;

public interface PlayerStatService {

    void addStatsToPlayer(Long playerId, Integer score, Long assistingPlayerId, League league) throws Exception;
    PlayerStat getPlayerStatById(Long playerId) throws Exception;
}
