package com.example.asecaserver.statistics;

import com.example.asecaserver.league.League;
import com.example.asecaserver.team.Team;
import jakarta.persistence.*;

@Entity
@Table
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
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
