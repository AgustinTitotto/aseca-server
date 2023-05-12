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

    @Override
    public List<League> findAll() {
        return repository.findAll();
    }

    @Override
    public League findById(Long id) throws Exception {
        return repository.findById(id).orElseThrow(() -> new Exception("No league exists with id: " + id));
    }

    @Override
    public League addLeague(String leagueName, List<String> teamNames, Date startDate, Date finishDate) throws Exception {
        validateDates(startDate, finishDate);
        List<Team> teams = teamService.saveTeamsAndPlayer(teamNames);
        League league = new League(leagueName);
        league.setTeams(teams);
        League savedLeague =  repository.save(league);
        externalApiService.createMatches(teams, startDate, finishDate, savedLeague.getId());
        return savedLeague;
    }

    @Override
    public List<Team> getTeams(Long leagueId) throws Exception {
        League league = findById(leagueId);
        return league.getTeams();
    }

    private void validateDates(Date startDate, Date finishDate) throws Exception {
        Date date = new Date();
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        startDate.setHours(1);
        if (startDate.before(date) || finishDate.before(startDate)) {
            throw new Exception("Invalid dates for creating league");
        }
    }


}
