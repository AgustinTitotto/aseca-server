package com.example.asecaserver.match;

import com.example.asecaserver.team.Team;
import com.example.asecaserver.team.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository, TeamRepository teamRepository) {
        this.matchRepository = matchRepository;
        this.teamRepository = teamRepository;
    }

    public void createMatch(Match match) {
        Team localTeam = teamRepository.findByTeamName(match.getLocalTeam().getTeamName());
        Team awayTeam = teamRepository.findByTeamName(match.getAwayTeam().getTeamName());
        if (localTeam == null || awayTeam == null) {
            throw new RuntimeException("One of the teams doesnt exist");
        }
        Boolean matchExists = matchRepository.matchExists(localTeam.getId(), awayTeam.getId());
        if (matchExists) {
            throw new RuntimeException("Match already exists");
        }
        match.setLocalTeam(localTeam);
        match.setAwayTeam(awayTeam);
        matchRepository.save(match);
    }
}
