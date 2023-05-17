package com.example.asecaserver.controller;

import com.example.asecaserver.model.Player;
import com.example.asecaserver.model.Team;
import com.example.asecaserver.service.impl.TeamServiceImpl;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:19006"})
@RequestMapping(path = "/team")
public class TeamController {

    private final TeamServiceImpl teamService;

    public TeamController(TeamServiceImpl service) {
        this.teamService = service;
    }

    @GetMapping("/get")
    public Team findById(@PathParam("id") Long id) throws Exception{
        return teamService.findById(id);
    }

//    @PostMapping("/add")
//    public Team addTeam(@RequestBody Team team) {
//        return teamService.insert(team);
//    }

//    @PostMapping("update")
//    public Team update(@RequestBody Team team) {
//        return teamService.update(team);
//    }

    @GetMapping("/getPlayers")
    public List<Player> getPlayers(@PathParam("id") Long teamId) throws Exception {
        return teamService.getPlayers(teamId);
    }

}
