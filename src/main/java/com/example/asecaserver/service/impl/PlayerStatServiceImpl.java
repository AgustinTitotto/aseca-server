package com.example.asecaserver.service.impl;

import com.example.asecaserver.model.League;
import com.example.asecaserver.model.Player;
import com.example.asecaserver.model.PlayerStat;
import com.example.asecaserver.repository.PlayerStatRepository;
import com.example.asecaserver.service.PlayerService;
import com.example.asecaserver.service.PlayerStatService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerStatServiceImpl implements PlayerStatService {

    private final PlayerStatRepository repository;
    private final PlayerService playerService;

    public PlayerStatServiceImpl(PlayerStatRepository repository, PlayerService playerService) {
        this.repository = repository;
        this.playerService = playerService;
    }

    @Override
    public PlayerStat getPlayerStatById(Long playerId) throws Exception {
        return repository.findByPlayerId(playerId).orElseThrow(() -> new Exception("No player stat exists with player id: " + playerId));
    }

    @Override
    public void createPlayerStat(List<Player> players, League league) {
        for (Player player: players) {
            PlayerStat playerStat = new PlayerStat();
            playerStat.setLeague(league);
            playerStat.setPlayer(player);
            repository.save(playerStat);
        }
    }

    @Override
    public void addStatsToPlayer(Long scoringPlayerId, Integer score, Long assistingPLayerId, League league) throws Exception {
        PlayerStat scoringPlayerStat = getPlayerStat(scoringPlayerId, league);
        if (score == 2) {
            scoringPlayerStat.setPoints2(scoringPlayerStat.getPoints2() + 1);
        } else if (score == 3) {
            scoringPlayerStat.setPoints3(scoringPlayerStat.getPoints3() + 1);
        } else throw new Exception("Cannot make a " + score + "point");
        scoringPlayerStat.setPointsScored((scoringPlayerStat.getPoints2() * 2) + (scoringPlayerStat.getPoints3() * 3));
        repository.save(scoringPlayerStat);

        if (assistingPLayerId != null) {
            PlayerStat playerAssistStat = getPlayerStat(assistingPLayerId, league);
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
