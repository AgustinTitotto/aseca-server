package com.example.asecaserver.controller;


import com.example.asecaserver.model.TeamStat;
import com.example.asecaserver.service.impl.TeamStatServiceImpl;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/leagueTable")
public class TeamStatController {

    private final TeamStatServiceImpl teamStatService;

    public TeamStatController(TeamStatServiceImpl teamStatService) {
        this.teamStatService = teamStatService;
    }

    @GetMapping("/get")
    public List<TeamStat> getLeagueTable(@PathParam("id") Long leagueId) {
        return teamStatService.getLeagueTable(leagueId);
    }
}
