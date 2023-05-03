package com.example.asecaserver.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @OneToOne
    private Team currentTeam;

    private Integer twoPointers;
    private Integer threePointer;
    private Integer assists;

    public Player() {

    }

    public Player(String name, Team currentTeam) {
        this.name = name;
        this.currentTeam = currentTeam;
        this.twoPointers = 0;
        this.threePointer = 0;
        this.assists = 0;
    }

}
