package com.example.asecaserver.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import java.util.Date;

@Entity
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Team localTeam;

    @OneToOne
    private Team awayTeam;

    @OneToOne
    private League league;

    private Date date;
    private Integer localScore;
    private Integer awayScore;
    private boolean hasEnded;

    public Match(Team localTeam, Team awayTeam, League league) {
        this.localTeam = localTeam;
        this.awayTeam = awayTeam;
        this.league = league;
        this.localScore = 0;
        this.awayScore = 0;
        this.hasEnded = false;
    }

    public Match() {
        this.localScore = 0;
        this.awayScore = 0;
        this.hasEnded = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Date getDate() {
//        return date;
//    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Team getLocalTeam() {
        return localTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public League getLeague() {
        return league;
    }

    public void setLocalTeam(Team localTeam) {
        this.localTeam = localTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public void setLocalScore(Integer localScore) {
        this.localScore = localScore;
    }

    public void setAwayScore(Integer awayScore) {
        this.awayScore = awayScore;
    }

    public boolean hasEnded() {
        return hasEnded;
    }

    public void setHasEnded(boolean hasEnded) {
        this.hasEnded = hasEnded;
    }

    public Integer getLocalScore() {
        return localScore;
    }

    public Integer getAwayScore() {
        return awayScore;
    }
}
