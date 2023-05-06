package com.example.asecaserver.service;

import com.example.asecaserver.model.League;

import java.util.List;

public interface LeagueService {
    League findById(Long id) throws Exception;
    List<League> findAll();
}
