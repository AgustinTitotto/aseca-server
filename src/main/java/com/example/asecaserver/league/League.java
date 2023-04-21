package com.example.asecaserver.league;


import com.example.asecaserver.team.Team;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "LEAGUE")
public class League {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;
    private String leagueName;
    private Integer maxTeams;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Team> teams = new ArrayList<>();

    public League(String leagueName, Integer maxTeams) {
        this.leagueName = leagueName;
        this.maxTeams = maxTeams;
    }

    public League() {

    }

    public Long getId() {
        return id;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public Integer getMaxTeams() {
        return maxTeams;
    }

    public List<Team> getTeams() {
        return teams;
    }
}
