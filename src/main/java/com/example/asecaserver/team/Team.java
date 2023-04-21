package com.example.asecaserver.team;

import com.example.asecaserver.statistics.Statistics;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class Team {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
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
