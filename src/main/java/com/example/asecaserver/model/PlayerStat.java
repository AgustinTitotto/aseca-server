package com.example.asecaserver.model;

import jakarta.persistence.*;

@Entity
public class PlayerStat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Player player;

    @OneToOne
    private League league;

    private Integer pointsScored;
    private Integer points2;
    private Integer points3;
    private Integer assists;

    public PlayerStat() {
        this.pointsScored = 0;
        this.points2 = 0;
        this.points3 = 0;
        this.assists = 0;
    }

    public Integer getPoints2() {
        return points2;
    }

    public void setPoints2(Integer points2) {
        this.points2 = points2;
    }

    public Integer getPoints3() {
        return points3;
    }

    public void setPoints3(Integer points3) {
        this.points3 = points3;
    }

    public Integer getPointsScored() {
        return pointsScored;
    }

    public void setPointsScored(Integer pointsScored) {
        this.pointsScored = pointsScored;
    }

    public Integer getAssists() {
        return assists;
    }

    public void setAssists(Integer assists) {
        this.assists = assists;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public Long getId() {
        return id;
    }

    public Player getPlayer() {
        return player;
    }

    public League getLeague() {
        return league;
    }
}
