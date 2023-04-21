package com.example.asecaserver.player;

import com.example.asecaserver.team.Team;
import jakarta.persistence.*;

@Entity
@Table
public class Player {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    private Team currentTeam;

    private Integer twoPointers;
    private Integer threePointer;



}
