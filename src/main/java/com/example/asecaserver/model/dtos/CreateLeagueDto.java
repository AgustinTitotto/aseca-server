package com.example.asecaserver.model.dtos;

import com.example.asecaserver.model.League;
import com.example.asecaserver.model.Team;

import java.util.List;

public class CreateLeagueDto {

    League league;
    List<Team> teams;

    public CreateLeagueDto(League league, List<Team> teams) {
        this.league = league;
        this.teams = teams;
    }

    public League getLeague() {
        return league;
    }

    public List<Team> getTeams() {
        return teams;
    }
}
