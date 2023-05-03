package com.example.asecaserver.service.impl;

import com.example.asecaserver.model.League;
import com.example.asecaserver.model.PlayerStat;
import com.example.asecaserver.model.Point;
import com.example.asecaserver.repository.PlayerStatRepository;
import com.example.asecaserver.service.PlayerService;
import com.example.asecaserver.service.PlayerStatService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerStatServiceImpl implements PlayerStatService {

    private PlayerStatRepository repository;
    private final PlayerServiceImpl playerService;

    public PlayerStatServiceImpl(PlayerStatRepository repository, PlayerServiceImpl playerService) {
        this.repository = repository;
        this.playerService = playerService;
    }

    public void addPlayerStat(Point point, League league) throws Exception {
        PlayerStat scoringPlayerStat = getPlayerStat(point.getScoringPlayer(), league);
        if (point.getScore() == 2) {
            scoringPlayerStat.setPoints2(scoringPlayerStat.getPoints2() + 1);
        }
        else if (point.getScore() == 3) {
            scoringPlayerStat.setPoints3(scoringPlayerStat.getPoints3() + 1);
        }
        else throw new Exception("Cannot make a " + point.getScore() + "point");
        scoringPlayerStat.setPointsScored((scoringPlayerStat.getPoints2() * 2) + (scoringPlayerStat.getPoints3() * 3));
        repository.save(scoringPlayerStat);

        if (point.getAssistPlayer() != null) {
            PlayerStat playerAssistStat = getPlayerStat(point.getAssistPlayer(), league);
            playerAssistStat.setAssists(playerAssistStat.getAssists() + 1);
            repository.save(playerAssistStat);
        }

    }

    private PlayerStat getPlayerStat(Long playerId, League league) throws Exception {
        PlayerStat playerStat;
        if (repository.findByPlayerId(playerId).isEmpty()){
            playerStat = new PlayerStat();
            playerStat.setPlayer(playerService.findById(playerId));
            playerStat.setLeague(league);
        }
        else playerStat = repository.findByPlayerId(playerId).get();
        return playerStat;
    }
}
