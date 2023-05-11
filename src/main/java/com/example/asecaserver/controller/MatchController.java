package com.example.asecaserver.controller;

import com.example.asecaserver.model.dtos.EndMatchDto;
import com.example.asecaserver.model.dtos.MatchDto;
import com.example.asecaserver.model.dtos.PointDto;
import com.example.asecaserver.service.impl.MatchServiceImpl;
import com.example.asecaserver.model.Match;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/match")
public class MatchController {

    private final MatchServiceImpl matchService;

    public MatchController(MatchServiceImpl matchServiceImpl) {
        this.matchService = matchServiceImpl;
    }

    @PostMapping("/create")
    public Match createMatch(@RequestBody MatchDto matchDto) throws Exception{
        return matchService.createMatch(matchDto);
    }

    @PostMapping("/addPoint")
    public void addPoint(@RequestBody PointDto point) throws Exception {
        matchService.addPoint(point);
    }

    @PostMapping("/end")
    public void endMatch(@RequestBody EndMatchDto endMatchDto) throws Exception {
        matchService.endMatch(endMatchDto.getMatchId(), endMatchDto.getLocalScore(), endMatchDto.getAwayScore());
    }

    @GetMapping("/leagueMatches")
    public List<Match> getLeagueMatches(@RequestBody Long leagueId) {
        return matchService.getLeagueMatches(leagueId);
    }

}
