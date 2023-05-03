package com.example.asecaserver.service.impl;

import com.example.asecaserver.model.League;
import com.example.asecaserver.repository.LeagueRepository;
import com.example.asecaserver.model.Team;
import com.example.asecaserver.service.LeagueService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeagueServiceImpl implements LeagueService {

    private final LeagueRepository repository;

    public LeagueServiceImpl(LeagueRepository repository) {
        this.repository = repository;
    }

    public League findById(Long id) throws Exception {
        return repository.findById(id).orElseThrow(() -> new Exception("No league exists with id: " + id));
    }

    public League addLeague(League league, List<Team> teams) {
        league.setTeams(teams);
        return repository.save(league);
    }

//    @Transactional
//    public void addTeamToLeague(Long leagueId, Long teamId) {
//        Optional<League> league = repository.findById(leagueId);
//        Optional<Team> team = teamRepository.findById(teamId);
//        boolean teamIsNotInLeague = true;
//        List<Team> teams = league.get().getTeams();
//        for (Team value : teams) {
//            if (value.getTeamName().equals(team.get().getTeamName())) {
//                teamIsNotInLeague = false;
//                break;
//            }
//        }
//        if (teamIsNotInLeague){
//            league.get().getTeams().add(team.get());
//            repository.save(league.get());
//        }
//
//    }
}
