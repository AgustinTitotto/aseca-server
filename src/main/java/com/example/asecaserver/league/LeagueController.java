package com.example.asecaserver.league;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("league")
public class LeagueController {

    private final LeagueService leagueService;

    @Autowired
    public LeagueController(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    @PostMapping("/create")
    public void createLeague(@RequestBody League league) {
        leagueService.createLeague(league);
    }

    @PostMapping("/addTeam")
    public void addTeamToLeague(@RequestParam Long leagueId, @RequestParam Long teamId){
        leagueService.addTeamToLeague(leagueId, teamId);
    }
}
