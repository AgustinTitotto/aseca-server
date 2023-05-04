package com.example.asecaserver.controller;


import com.example.asecaserver.model.TeamStat;
import com.example.asecaserver.service.impl.TeamStatServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/leagueTable")
public class TeamStatController {

    private final TeamStatServiceImpl teamStatService;

    public TeamStatController(TeamStatServiceImpl teamStatService) {
        this.teamStatService = teamStatService;
    }

    @GetMapping("/get")
    public List<TeamStat> getLeagueTable(@RequestBody Long leagueId) {
        return teamStatService.getLeagueTable(leagueId);
    }
}
