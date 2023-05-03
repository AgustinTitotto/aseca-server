package com.example.asecaserver.model;

public class Point {

    private Long teamId;
    private Long scoringPlayerId;
    private Integer score;
    private Long assistPlayerId;

    public Point(Long teamId, Long scoringPlayerId, Integer score, Long assistPlayer) {
        this.teamId = teamId;
        this.scoringPlayerId = scoringPlayerId;
        this.score = score;
        this.assistPlayerId = assistPlayer;
    }

    public Long getTeam() {
        return teamId;
    }

    public Long getScoringPlayer() {
        return scoringPlayerId;
    }

    public Integer getScore() {
        return score;
    }

    public Long getAssistPlayer() {
        return assistPlayerId;
    }
}
