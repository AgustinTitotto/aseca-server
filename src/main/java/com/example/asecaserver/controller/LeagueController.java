package com.example.asecaserver.controller;

import com.example.asecaserver.service.LeagueService;
import com.example.asecaserver.service.impl.LeagueServiceImpl;
import com.example.asecaserver.model.League;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/league")
public class LeagueController {

    private final LeagueServiceImpl leagueService;

    public LeagueController(LeagueServiceImpl leagueService) {
        this.leagueService = leagueService;
    }

    @GetMapping
    public League getLeague(@PathParam("id") Long id) throws Exception{
        return leagueService.findById(id);
    }

    @PostMapping("/add")
    public League addLeague(@RequestBody League league) {
        return leagueService.addLeague(league);
    }
}
