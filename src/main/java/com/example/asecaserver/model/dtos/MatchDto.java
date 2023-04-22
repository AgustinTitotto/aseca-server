package com.example.asecaserver.model.dtos;

public class MatchDto {

    private Long localTeamId;
    private Long awayTeamId;
    private Long leagueId;

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
