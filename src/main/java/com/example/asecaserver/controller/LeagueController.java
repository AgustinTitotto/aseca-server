package com.example.asecaserver.controller;

import com.example.asecaserver.model.Team;
import com.example.asecaserver.model.dtos.CreateLeagueDto;
import com.example.asecaserver.service.PlayerService;
import com.example.asecaserver.service.impl.LeagueServiceImpl;
import com.example.asecaserver.model.League;
import com.example.asecaserver.service.impl.PlayerServiceImpl;
import com.example.asecaserver.service.impl.TeamServiceImpl;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/league")
public class LeagueController {

    private final LeagueServiceImpl leagueService;

    public LeagueController(LeagueServiceImpl leagueService) {
        this.leagueService = leagueService;

    }

    @GetMapping("/get")
    public League getLeague(@PathParam("id") Long id) throws Exception{
        return leagueService.findById(id);
    }

    @PostMapping("/add")
    public League addLeague(@RequestBody CreateLeagueDto createLeagueDto) {
        return leagueService.addLeague(createLeagueDto.getLeague(), createLeagueDto.getTeams());
    }

    @GetMapping("/getTeams")
    public List<Team> getTeams(@RequestBody Long leagueId) throws Exception {
        return leagueService.getTeams(leagueId);
    }

//    @GetMapping
//    public List<League> getLeagues() {
//
//    }
//
//    @GetMapping
//    public List<Team> getTeams(@RequestBody League league) {
//
//    }


}
