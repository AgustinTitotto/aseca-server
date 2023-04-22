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

    private Integer localTwoPointer;
    private Integer localThreePointer;

    private Integer awayTwoPointer;
    private Integer awayThreePointer;

    public Match(Team localTeam, Team awayTeam, League league) {
        this.localTeam = localTeam;
        this.awayTeam = awayTeam;
        this.league = league;
        this.localTwoPointer = 0;
        this.localThreePointer = 0;
        this.awayTwoPointer = 0;
        this.awayThreePointer = 0;
    }

    public Match() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

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

    public Integer getLocalTwoPointer() {
        return localTwoPointer;
    }

    public Integer getLocalThreePointer() {
        return localThreePointer;
    }

    public Integer getAwayTwoPointer() {
        return awayTwoPointer;
    }

    public Integer getAwayThreePointer() {
        return awayThreePointer;
    }

    public Integer getLocalTotalScore() {
        return (localTwoPointer * 2) + (localThreePointer * 3);
    }

    public Integer getAwayTotalScore() {
        return (awayTwoPointer * 2) + (awayThreePointer * 3);
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
}
