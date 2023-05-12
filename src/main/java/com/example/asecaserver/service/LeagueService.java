package com.example.asecaserver.service;

import com.example.asecaserver.model.League;
import com.example.asecaserver.model.Team;

import java.util.Date;
import java.util.List;

public interface LeagueService {

    League findById(Long id) throws Exception;
    List<League> findAll();
    League addLeague(String leagueName, List<String> teamNames, Date startDate, Date finishDate) throws Exception;
    List<Team> getTeams(Long leagueId) throws Exception;
}
