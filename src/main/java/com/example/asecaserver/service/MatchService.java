package com.example.asecaserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface MatchService {
    void createMatches(String string, Long leagueId) throws Exception;
}
