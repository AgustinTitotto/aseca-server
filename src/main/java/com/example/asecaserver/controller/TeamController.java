package com.example.asecaserver.controller;

import com.example.asecaserver.model.Team;
import com.example.asecaserver.service.impl.TeamServiceImpl;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/team")
public class TeamController {

    private final TeamServiceImpl service;

    public TeamController(TeamServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/get")
    public Team findById(@PathParam("id") Long id) throws Exception{
        return service.findById(id);
    }

    @PostMapping("/add")
    public Team addTeam(@RequestBody Team team) {
        return service.insert(team);
    }

    @PostMapping("update")
    public Team update(@RequestBody Team team) {
        return service.update(team);
    }

}
