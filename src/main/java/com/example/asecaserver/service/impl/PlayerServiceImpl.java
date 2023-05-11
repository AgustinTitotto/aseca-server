package com.example.asecaserver.service.impl;

import com.example.asecaserver.model.Player;
import com.example.asecaserver.repository.PlayerRepository;
import com.example.asecaserver.service.PlayerService;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<Player> savePlayers(int playerPerTeam) {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < playerPerTeam; i++) {
            Faker faker = new Faker();
            Player player = new Player(faker.name().fullName());
            players.add(player);
            repository.save(player);
        }
        return players;
    }
}
