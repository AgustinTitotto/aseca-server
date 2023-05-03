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
    private final TeamServiceImpl teamService;
    private final PlayerServiceImpl playerService;

    public LeagueController(LeagueServiceImpl leagueService, TeamServiceImpl teamService, PlayerServiceImpl playerService) {
        this.leagueService = leagueService;
        this.teamService = teamService;
        this.playerService = playerService;
    }

    @GetMapping
    public League getLeague(@PathParam("id") Long id) throws Exception{
        return leagueService.findById(id);
    }

    @PostMapping("/add")
    public League addLeague(@RequestBody CreateLeagueDto createLeagueDto) {
        teamService.saveTeams(createLeagueDto.getTeams());
        playerService.savePlayer(createLeagueDto.getTeams());
        return leagueService.addLeague(createLeagueDto.getLeague(), createLeagueDto.getTeams());
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
