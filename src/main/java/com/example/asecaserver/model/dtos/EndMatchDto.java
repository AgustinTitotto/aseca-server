package com.example.asecaserver.model.dtos;


public class EndMatchDto {

    private Long matchId;
    private Integer localScore;
    private Integer awayScore;

    public EndMatchDto(Long matchId, Integer localScore, Integer awayScore) {
        this.matchId = matchId;
        this.localScore = localScore;
        this.awayScore = awayScore;
    }

    public Long getMatchId() {
        return matchId;
    }

    public Integer getLocalScore() {
        return localScore;
    }

    public Integer getAwayScore() {
        return awayScore;
    }
}
