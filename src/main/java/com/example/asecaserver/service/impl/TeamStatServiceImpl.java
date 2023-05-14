package com.example.asecaserver.service.impl;

import com.example.asecaserver.model.League;
import com.example.asecaserver.model.Team;
import com.example.asecaserver.model.TeamStat;
import com.example.asecaserver.repository.TeamStatRepository;
import com.example.asecaserver.service.PlayerStatService;
import com.example.asecaserver.service.TeamStatService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamStatServiceImpl implements TeamStatService {

    private final TeamStatRepository repository;
    private final PlayerStatService playerStatService;

    public TeamStatServiceImpl(TeamStatRepository repository, PlayerStatService playerStatService) {
        this.repository = repository;
        this.playerStatService = playerStatService;
    }

    public TeamStat getStatByLeagueIdAndTeamId(Long id, Long id1) throws Exception {
        return repository.findByLeagueIdAndTeamId(id, id1).orElseThrow(() -> new Exception("No team stat exists with league id: " + id + " and team id: " + id1));
    }

    public void saveStat(TeamStat localStats) {
        repository.save(localStats);
    }

    public List<TeamStat> getLeagueTable(Long leagueId) {
        return repository.findAllByLeagueId(leagueId);
    }

    @Override
    public void createTeamAndPlayerStats(List<Team> teams, League league) {
        for (Team team : teams) {
            TeamStat teamStat = new TeamStat();
            teamStat.setTeam(team);
            teamStat.setLeague(league);
            repository.save(teamStat);
            playerStatService.createPlayerStat(team.getPlayers(), league);
        }
    }
}
