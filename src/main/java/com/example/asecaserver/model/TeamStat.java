package com.example.asecaserver.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class TeamStat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Team team;

    @OneToOne
    private League league;

    private Integer matchesPlayed;
    private Integer wins;
    private Integer losses;
    private Double winPercentage;
    private Integer pointInFavour;
    private Integer winStreak;

    public TeamStat() {
        this.matchesPlayed = 0;
        this.wins = 0;
        this.losses = 0;
        this.winPercentage = 0.0;
        this.pointInFavour = 0;
        this.winStreak = 0;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public Integer getMatchesPlayed() {
        return matchesPlayed;
    }

    public void setMatchesPlayed(Integer matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getLosses() {
        return losses;
    }

    public void setLosses(Integer losses) {
        this.losses = losses;
    }

    public Integer getWinStreak() {
        return winStreak;
    }

    public void setWinStreak(Integer winStreak) {
        this.winStreak = winStreak;
    }

    public Integer getPointInFavour() {
        return pointInFavour;
    }

    public void setPointInFavour(Integer pointInFavour) {
        this.pointInFavour = pointInFavour;
    }

    public void setWinPercentage(Double winPercentage) {
        this.winPercentage = winPercentage;
    }

    public Long getId() {
        return id;
    }

    public Team getTeam() {
        return team;
    }

    public League getLeague() {
        return league;
    }

    public Double getWinPercentage() {
        return winPercentage;
    }
}
