package com.example.asecaserver.team;

import com.example.asecaserver.league.League;
import jakarta.persistence.*;

@Entity
@Table
public class Team {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    private String teamName;
    /*private Double winPercentage;
    private Integer matchesPlayed;
    private Integer wins;
    private Integer losses;
    private Double homeWinPercentage;
    private Double awayWinPercentage;
    private Integer last10Games;*/

    @ManyToOne
    private League league;

    public Team(String teamName){
        this.teamName = teamName;
    }

    public Team() {

    }

    public Long getId() {
        return id;
    }

    public String getTeamName() {
        return teamName;
    }

}
