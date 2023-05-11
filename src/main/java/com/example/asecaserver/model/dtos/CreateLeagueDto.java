package com.example.asecaserver.model.dtos;

import java.util.Date;
import java.util.List;

public class CreateLeagueDto {

    private String leagueName;
    private List<String> teams;
    private Date startDate;
    private Date finishDate;

    public CreateLeagueDto(String leagueName, List<String> teams, Date startDate, Date finishDate) {
        this.leagueName = leagueName;
        this.teams = teams;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public List<String> getTeams() {
        return teams;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }
}
