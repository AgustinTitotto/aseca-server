package com.example.asecaserver.service.impl;

import com.example.asecaserver.model.League;
import com.example.asecaserver.model.Match;
import com.example.asecaserver.repository.LeagueRepository;
import com.example.asecaserver.repository.MatchRepository;
import com.example.asecaserver.model.Team;
import com.example.asecaserver.repository.TeamRepository;
import com.example.asecaserver.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;
    private final LeagueRepository leagueRepository;

    public MatchServiceImpl(MatchRepository matchRepository, TeamRepository teamRepository, LeagueRepository leagueRepository) {
        this.matchRepository = matchRepository;
        this.teamRepository = teamRepository;
        this.leagueRepository = leagueRepository;
    }

//    public void createMatch(Match match) {
//        Team localTeam = teamRepository.findByTeamName(match.getLocalTeam().getTeamName());
//        Team awayTeam = teamRepository.findByTeamName(match.getAwayTeam().getTeamName());
//        Optional<League> league = leagueRepository.findById(match.getLeague().getId());
//        if (localTeam == null || awayTeam == null) {
//            throw new RuntimeException("One of the teams doesnt exist");
//        }
//        Boolean matchExists = matchRepository.matchExists(localTeam.getId(), awayTeam.getId());
//        if (matchExists) {
//            throw new RuntimeException("Match already exists");
//        }
//        match.setLocalTeam(localTeam);
//        match.setAwayTeam(awayTeam);
//        match.setLeague(league.get());
//        matchRepository.save(match);
//    }
}
