package com.example.asecaserver.service;

import com.example.asecaserver.model.Team;

public interface TeamService {

    Team findById(Long id) throws Exception;
    Team insert(Team team);
}