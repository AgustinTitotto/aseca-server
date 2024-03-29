package com.example.asecaserver.model.dtos;

public class PointDto {

    public Long matchId;
    public Long teamId;
    public Long scoringPlayerId;
    public Integer score;
    public Long assistPlayerId;

    public PointDto(Long matchId, Long teamId, Long scoringPlayerId, Integer score, Long assistPlayerId) {
        this.matchId = matchId;
        this.teamId = teamId;
        this.scoringPlayerId = scoringPlayerId;
        this.score = score;
        this.assistPlayerId = assistPlayerId;
    }

    public Long getMatchId() {
        return matchId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public Long getScoringPlayerId() {
        return scoringPlayerId;
    }

    public Integer getScore() {
        return score;
    }

    public Long getAssistPlayerId() {
        return assistPlayerId;
    }
}
