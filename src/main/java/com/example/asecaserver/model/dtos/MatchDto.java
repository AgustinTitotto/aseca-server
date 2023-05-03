package com.example.asecaserver.model.dtos;

public class MatchDto {

    private Long localTeamId;
    private Long awayTeamId;
    private Long leagueId;

    public MatchDto(Long localTeamId, Long awayTeamId, Long leagueId) {
        this.localTeamId = localTeamId;
        this.awayTeamId = awayTeamId;
        this.leagueId = leagueId;
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
