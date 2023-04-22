package com.example.asecaserver.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Statistics {

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
    //private Double homeWinPercentage;
    //private Double awayWinPercentage;
    private Integer last10Games;
    private Integer pointsInFavor;

    public Statistics() {

    }
}
