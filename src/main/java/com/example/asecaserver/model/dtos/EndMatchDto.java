package com.example.asecaserver.model.dtos;

import com.example.asecaserver.model.Point;

import java.util.List;

public class EndMatchDto {

    private Long matchId;
    private List<Point> points;

    public EndMatchDto(Long matchId, List<Point> points) {
        this.matchId = matchId;
        this.points = points;
    }

    public Long getMatchId() {
        return matchId;
    }

    public List<Point> getPoints() {
        return points;
    }
}
