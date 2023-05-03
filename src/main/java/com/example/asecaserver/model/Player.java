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

    private Integer twoPointers;
    private Integer threePointer;
    private Integer assists;

    public Player() {

    }

    public Player(String name) {
        this.name = name;
        this.twoPointers = 0;
        this.threePointer = 0;
        this.assists = 0;
    }

}
