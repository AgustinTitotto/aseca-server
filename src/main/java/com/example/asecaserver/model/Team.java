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
    private List<Player> players;

    public Team(String teamName) {
        this.teamName = teamName;
    }

    public Team() {

    }

    public Long getId() {
        return id;
    }

    //Para crear los equipos
    public String getTeamName() {
        return teamName;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
