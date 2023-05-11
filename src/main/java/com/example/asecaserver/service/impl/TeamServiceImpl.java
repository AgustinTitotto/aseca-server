package com.example.asecaserver.service.impl;

import com.example.asecaserver.model.Player;
import com.example.asecaserver.model.Team;
import com.example.asecaserver.repository.TeamRepository;
import com.example.asecaserver.service.PlayerService;
import com.example.asecaserver.service.TeamService;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository repository;
    private final PlayerService playerService;

    public TeamServiceImpl(TeamRepository repository, PlayerService playerService) {
        this.repository = repository;
        this.playerService = playerService;
    }

    @Override
    public Team findById(Long id) throws Exception{
        return repository.findById(id).orElseThrow(() -> new Exception("No team exists with id: " + id));
    }

    @Override
    public List<Team> saveTeamsAndPlayer(List<String> teamNames) {
        List<Team> teams = new ArrayList<>();
        int playerPerTeam = 12;
        for (String teamName : teamNames) {
            Team team = new Team(teamName);
            List<Player> players = playerService.savePlayers(playerPerTeam);
            team.setPlayers(players);
            Team savedTeam = repository.save(team);
            teams.add(savedTeam);
        }
        return teams;
    }

    public List<Player> getPlayers(Long teamId) throws Exception {
        Optional<Team> team = repository.findById(teamId);
        if (team.isPresent()){
            return team.get().getPlayers();
        }
        else throw new Exception("Team does not exist");
    }
}
