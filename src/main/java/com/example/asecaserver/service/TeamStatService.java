package com.example.asecaserver.service;

import com.example.asecaserver.model.TeamStat;

import java.util.List;

public interface TeamStatService {

    TeamStat getStatByLeagueIdAndTeamId(Long leagueId, Long teamId) throws Exception;
    void saveStat(TeamStat teamStat);
    List<TeamStat> getLeagueTable(Long leagueId);
}
