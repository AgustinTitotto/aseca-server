package com.example.asecaserver.service.impl;

import com.example.asecaserver.model.League;
import com.example.asecaserver.repository.LeagueRepository;
import com.example.asecaserver.model.Team;
import com.example.asecaserver.service.LeagueService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeagueServiceImpl implements LeagueService {

    private final LeagueRepository repository;
    private final TeamServiceImpl teamService;
    private final PlayerServiceImpl playerService;

    public LeagueServiceImpl(LeagueRepository repository, TeamServiceImpl teamService, PlayerServiceImpl playerService) {
        this.repository = repository;
        this.teamService = teamService;
        this.playerService = playerService;
    }

    public League findById(Long id) throws Exception {
        return repository.findById(id).orElseThrow(() -> new Exception("No league exists with id: " + id));
    }

    public League addLeague(League league, List<Team> teams) {
        teamService.saveTeams(teams);
        playerService.savePlayer(teams);
        league.setTeams(teams);
        return repository.save(league);
    }

    public List<Team> getTeams(Long leagueId) throws Exception {
        Optional<League> league = repository.findById(leagueId);
        if (league.isPresent()){
            return league.get().getTeams();
        }
        else throw new Exception("League does not exist");
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
