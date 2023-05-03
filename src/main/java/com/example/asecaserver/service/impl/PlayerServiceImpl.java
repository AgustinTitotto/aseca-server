package com.example.asecaserver.service.impl;

import com.example.asecaserver.model.Player;
import com.example.asecaserver.repository.PlayerRepository;
import com.example.asecaserver.service.PlayerService;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImpl implements PlayerService {

    final private PlayerRepository repository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.repository = playerRepository;
    }

    @Override
    public Player findById(Long id) throws Exception{
        return repository.findById(id).orElseThrow(() -> new Exception("No player exists with id: " + id));
    }

    public void savePlayer(Player player) {
        repository.save(player);
    }
}
