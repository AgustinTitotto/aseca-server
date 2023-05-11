package com.example.asecaserver.model.dtos;

import java.util.Date;

public class MatchDto {

    public Date date;
    public Long localTeamId;
    public Long awayTeamId;
    public Long leagueId;

    public MatchDto(Date date, Long localTeamId, Long awayTeamId, Long leagueId) {
        this.date = date;
        this.localTeamId = localTeamId;
        this.awayTeamId = awayTeamId;
        this.leagueId = leagueId;
    }


    public Date getDate() {
        return date;
    }

    public Long getLocalTeamId() {
        return localTeamId;
    }

    public Long getAwayTeamId() {
        return awayTeamId;
    }

    public Long getLeagueId() {
        return leagueId;
    }
}
