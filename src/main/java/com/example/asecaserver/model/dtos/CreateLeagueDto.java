package com.example.asecaserver.model.dtos;

import java.util.List;

public class CreateLeagueDto {

    private String leagueName;
    private List<String> teamNames;

    public CreateLeagueDto(String leagueName, List<String> teams) {
        this.leagueName = leagueName;
        this.teamNames = teams;
    }

    public String getLeague() {
        return leagueName;
    }

    public List<String> getTeams() {
        return teamNames;
    }
}
