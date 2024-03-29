package com.example.asecaserver.controller;

import com.example.asecaserver.model.Team;
import com.example.asecaserver.model.dtos.CreateLeagueDto;
import com.example.asecaserver.service.LeagueService;
import com.example.asecaserver.service.impl.LeagueServiceImpl;
import com.example.asecaserver.model.League;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:19006"})
@RequestMapping("/league")
public class LeagueController {

    private final LeagueService leagueService;

    public LeagueController(LeagueService leagueService) {
        this.leagueService = leagueService;

    }

    @GetMapping()
    public League getLeague(@PathParam("id") Long id) throws Exception{
        return leagueService.findById(id);
    }

    @GetMapping("/all")
    public List<League> getLeagues() {
        return leagueService.findAll();
    }

    @PostMapping("/add")
    public League addLeague(@RequestBody CreateLeagueDto createLeagueDto) throws Exception {
        return leagueService.addLeague(createLeagueDto.getLeagueName(), createLeagueDto.getTeams(), createLeagueDto.getStartDate(), createLeagueDto.getFinishDate());
    }

    @GetMapping("/getTeams")
    public List<Team> getTeams(@PathParam("id") Long leagueId) throws Exception {
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
