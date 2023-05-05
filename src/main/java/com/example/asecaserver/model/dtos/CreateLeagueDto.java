package com.example.asecaserver.model.dtos;

import java.util.List;

public class CreateLeagueDto {

    String leagueName;
    List<String> teamNames;

    public CreateLeagueDto(String league, List<String> teams) {
        this.leagueName = league;
        this.teamNames = teams;
    }

    public String getLeague() {
        return leagueName;
    }

    public List<String> getTeams() {
        return teamNames;
    }
}
