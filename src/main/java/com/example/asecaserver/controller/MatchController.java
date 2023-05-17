package com.example.asecaserver.controller;

import com.example.asecaserver.model.dtos.EndMatchDto;
import com.example.asecaserver.model.dtos.MatchDto;
import com.example.asecaserver.model.dtos.PointDto;
import com.example.asecaserver.service.impl.MatchServiceImpl;
import com.example.asecaserver.model.Match;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:19006"})
@RequestMapping("/match")
public class MatchController {

    private final MatchServiceImpl matchService;

    public MatchController(MatchServiceImpl matchServiceImpl) {
        this.matchService = matchServiceImpl;
    }

//    @PostMapping("/create")
//    public Match createMatch(@RequestBody MatchDto matchDto) throws Exception{
//        return matchService.createMatch(matchDto);
//    }

    @PostMapping("/addPoint")
    public Match addPoint(@RequestBody PointDto point) throws Exception {
        return matchService.addPoint(point);
    }

    @PostMapping("/end")
    public void endMatch(@RequestBody EndMatchDto endMatchDto) throws Exception {
        matchService.endMatch(endMatchDto.getMatchId());
    }

    @GetMapping("/leagueMatches")
    public List<Match> getLeagueMatches(@PathParam("id") Long leagueId) {
        return matchService.getLeagueMatches(leagueId);
    }

}
