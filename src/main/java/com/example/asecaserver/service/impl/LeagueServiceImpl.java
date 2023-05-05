package com.example.asecaserver.service.impl;

import com.example.asecaserver.model.League;
import com.example.asecaserver.model.Player;
import com.example.asecaserver.repository.LeagueRepository;
import com.example.asecaserver.model.Team;
import com.example.asecaserver.service.LeagueService;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LeagueServiceImpl implements LeagueService {

    private final LeagueRepository repository;
    private final TeamServiceImpl teamService;
    private final PlayerServiceImpl playerService;

    public LeagueServiceImpl(LeagueRepository repository, TeamServiceImpl teamService, PlayerServiceImpl playerService) {
        this.repository = repository;
        this.teamService = teamService;
        this.playerService = playerService;
    }

    public League findById(Long id) throws Exception {
        return repository.findById(id).orElseThrow(() -> new Exception("No league exists with id: " + id));
    }

    public League addLeague(String leagueName, List<String> teamNames) {
        List<Team> teams = saveTeamAndPlayer(teamNames);
        League league = new League(leagueName);
        league.setTeams(teams);
        return repository.save(league);
    }

    private List<Team> saveTeamAndPlayer(List<String> teamNames) {
        List<Team> teams = new ArrayList<>();
        int playerPerTeam = 12;
        for (String teamName : teamNames) {
            Team team = new Team(teamName);
            List<Player> players = new ArrayList<>();
            for (int i = 0; i < playerPerTeam; i++) {
                Faker faker = new Faker();
                Player player = new Player(faker.name().fullName());
                players.add(player);
                playerService.savePlayer(player);
            }
            teams.add(team);
            team.setPlayers(players);
            teamService.saveTeam(team);
        }
        return teams;
    }

    public List<Team> getTeams(Long leagueId) throws Exception {
        Optional<League> league = repository.findById(leagueId);
        if (league.isPresent()){
            return league.get().getTeams();
        }
        else throw new Exception("League does not exist");
    }

}
