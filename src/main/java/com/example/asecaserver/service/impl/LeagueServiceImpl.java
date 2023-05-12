package com.example.asecaserver.service.impl;

import com.example.asecaserver.model.League;
import com.example.asecaserver.repository.LeagueRepository;
import com.example.asecaserver.model.Team;
import com.example.asecaserver.service.ExternalApiService;
import com.example.asecaserver.service.LeagueService;
import com.example.asecaserver.service.TeamService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LeagueServiceImpl implements LeagueService {

    private final LeagueRepository repository;
    private final TeamService teamService;
    private final ExternalApiService externalApiService;


    public LeagueServiceImpl(LeagueRepository repository, TeamService teamService, ExternalApiService externalApiService) {
        this.repository = repository;
        this.teamService = teamService;
        this.externalApiService = externalApiService;
    }

    public List<League> findAll() {
        return repository.findAll();
    }

    public League findById(Long id) throws Exception {
        return repository.findById(id).orElseThrow(() -> new Exception("No league exists with id: " + id));
    }

    public League addLeague(String leagueName, List<String> teamNames, Date startDate, Date finishDate) throws Exception {
        List<Team> teams = teamService.saveTeamsAndPlayer(teamNames);
        League league = new League(leagueName);
        league.setTeams(teams);
        League savedLeague =  repository.save(league);
        externalApiService.createMatches(teams, startDate, finishDate, savedLeague.getId());
        return savedLeague;
    }

    public List<Team> getTeams(Long leagueId) throws Exception {
        League league = findById(leagueId);
        return league.getTeams();
    }

}
