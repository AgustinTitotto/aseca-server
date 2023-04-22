package com.example.asecaserver.service.impl;

import com.example.asecaserver.model.Match;
import com.example.asecaserver.model.dtos.MatchDto;
import com.example.asecaserver.repository.MatchRepository;
import com.example.asecaserver.service.MatchService;
import org.springframework.stereotype.Service;

@Service
public class MatchServiceImpl implements MatchService {

    private final MatchRepository repository;
    private final LeagueServiceImpl leagueService;
    private final TeamServiceImpl teamService;

    public MatchServiceImpl(MatchRepository repository, LeagueServiceImpl leagueService, TeamServiceImpl teamService) {
        this.repository = repository;
        this.leagueService = leagueService;
        this.teamService = teamService;
    }

    public Match createMatch(MatchDto matchDto) throws Exception {
        Match match = new Match();
        match.setLocalTeam(teamService.findById(matchDto.getLocalTeamId()));
        match.setAwayTeam(teamService.findById(matchDto.getAwayTeamId()));
        match.setLeague(leagueService.findById(matchDto.getLeagueId()));
        if (matchAlreadyExists(match)) throw new Exception("This match already exists on this league");
        return repository.save(match);
    }

    private boolean matchAlreadyExists(Match match) {
        return repository.findByLocalTeamIdAndAwayTeamIdAndLeagueId(
                match.getLocalTeam().getId(),
                match.getAwayTeam().getId(),
                match.getLeague().getId()
        ).isPresent();
    }
}
