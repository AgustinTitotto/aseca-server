package com.example.asecaserver.service;

import com.example.asecaserver.model.Player;

import java.util.List;

public interface PlayerService {
    Player findById(Long id) throws Exception;
    List<Player> savePlayers(int playerPerTeam);
}
