package com.example.asecaserver.league;


import jakarta.persistence.*;

@Entity
@Table
public class League {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;
    private String leagueName;
    private Integer maxTeams;

    public League(String leagueName, Integer maxTeams) {
        this.leagueName = leagueName;
        this.maxTeams = maxTeams;
    }

    public League() {

    }

    public String getLeagueName() {
        return leagueName;
    }

    public Integer getMaxTeams() {
        return maxTeams;
    }
}
