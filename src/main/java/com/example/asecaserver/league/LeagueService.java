package com.example.asecaserver.league;

import com.example.asecaserver.team.Team;
import com.example.asecaserver.team.TeamRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeagueService {

    private final LeagueRepository leagueRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public LeagueService(LeagueRepository leagueRepository, TeamRepository teamRepository) {
        this.leagueRepository = leagueRepository;
        this.teamRepository = teamRepository;
    }

    public void createLeague(League league) {
        leagueRepository.save(league);
    }

    @Transactional
    public void addTeamToLeague(Long leagueId, Long teamId) {
        Optional<League> league = leagueRepository.findById(leagueId);
        Optional<Team> team = teamRepository.findById(teamId);
        boolean teamIsNotInLeague = true;
        List<Team> teams = league.get().getTeams();
        for (Team value : teams) {
            if (value.getTeamName().equals(team.get().getTeamName())) {
                teamIsNotInLeague = false;
                break;
            }
        }
        if (teamIsNotInLeague){
            league.get().getTeams().add(team.get());
            leagueRepository.save(league.get());
        }

    }
}
