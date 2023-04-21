package com.example.asecaserver.match;

import com.example.asecaserver.league.League;
import com.example.asecaserver.league.LeagueRepository;
import com.example.asecaserver.team.Team;
import com.example.asecaserver.team.TeamRepository;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;
    private final LeagueRepository leagueRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository, TeamRepository teamRepository, LeagueRepository leagueRepository) {
        this.matchRepository = matchRepository;
        this.teamRepository = teamRepository;
        this.leagueRepository = leagueRepository;
    }

    public void createMatch(Match match) {
        Team localTeam = teamRepository.findByTeamName(match.getLocalTeam().getTeamName());
        Team awayTeam = teamRepository.findByTeamName(match.getAwayTeam().getTeamName());
        Optional<League> league = leagueRepository.findById(match.getLeague().getId());
        if (localTeam == null || awayTeam == null) {
            throw new RuntimeException("One of the teams doesnt exist");
        }
        Boolean matchExists = matchRepository.matchExists(localTeam.getId(), awayTeam.getId());
        if (matchExists) {
            throw new RuntimeException("Match already exists");
        }
        match.setLocalTeam(localTeam);
        match.setAwayTeam(awayTeam);
        match.setLeague(league.get());
        matchRepository.save(match);
    }
}
