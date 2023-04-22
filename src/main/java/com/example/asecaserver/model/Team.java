package com.example.asecaserver.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String teamName;

    @OneToMany
    private List<Statistics> statistics;

    public Team(String teamName) {
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
