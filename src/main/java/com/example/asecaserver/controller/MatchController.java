package com.example.asecaserver.controller;

import com.example.asecaserver.service.impl.MatchServiceImpl;
import com.example.asecaserver.model.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/match")
public class MatchController {

    private final MatchServiceImpl matchServiceImpl;

    public MatchController(MatchServiceImpl matchServiceImpl) {
        this.matchServiceImpl = matchServiceImpl;
    }

    @PostMapping
    public void createMatch(@RequestBody Match match) {
//        matchServiceImpl.createMatch(match);
    }
}
