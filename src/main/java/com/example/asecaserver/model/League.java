package com.example.asecaserver.model;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class League {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
