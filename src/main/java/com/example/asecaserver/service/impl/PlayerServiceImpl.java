package com.example.asecaserver.service.impl;

import com.example.asecaserver.model.Player;
import com.example.asecaserver.repository.PlayerRepository;
import com.example.asecaserver.service.PlayerService;

public class PlayerServiceImpl implements PlayerService {

    final private PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player findById(Long id) throws Exception{
        return playerRepository.findById(id).orElseThrow(() -> new Exception("No player exists with id: " + id));
    }
}
