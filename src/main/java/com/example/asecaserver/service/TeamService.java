package com.example.asecaserver.service;

import com.example.asecaserver.model.Player;
import com.example.asecaserver.model.Team;

import java.util.List;

public interface TeamService {

    Team findById(Long id) throws Exception;
    List<Team> saveTeamsAndPlayer(List<String> teamNames);
    List<Player> getPlayers(Long teamId) throws Exception;
}
