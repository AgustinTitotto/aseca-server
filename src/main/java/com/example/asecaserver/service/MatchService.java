package com.example.asecaserver.service;


import com.example.asecaserver.model.Match;
import com.example.asecaserver.model.dtos.MatchDto;
import com.example.asecaserver.model.dtos.PointDto;

import java.util.List;

public interface MatchService {

    Match findById(Long id) throws Exception;
    Match createMatch(MatchDto matchDto) throws Exception;
    Match addPoint(PointDto pointDto) throws Exception;
    void endMatch(Long matchId) throws Exception;
    List<Match> getLeagueMatches(Long leagueId);
    void createMatches(String string, Long leagueId) throws Exception;
}
