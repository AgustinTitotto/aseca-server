package com.example.asecaserver.service.impl;

import com.example.asecaserver.model.Player;
import com.example.asecaserver.model.Team;
import com.example.asecaserver.repository.PlayerRepository;
import com.example.asecaserver.service.PlayerService;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    final private PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player findById(Long id) throws Exception{
        return playerRepository.findById(id).orElseThrow(() -> new Exception("No player exists with id: " + id));
    }

    public void savePlayer(List<Team> teams) {
        int playerPerTeam = 12;
        for (Team team : teams) {
            for (int i = 0; i < playerPerTeam; i++) {
                Faker faker = new Faker();
                Player player = new Player(faker.name().fullName(), team);
                playerRepository.save(player);
            }
        }
    }
}
