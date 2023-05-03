package com.example.asecaserver.controller;

import com.example.asecaserver.model.dtos.MatchDto;
import com.example.asecaserver.service.impl.MatchServiceImpl;
import com.example.asecaserver.model.Match;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/match")
public class MatchController {

    private final MatchServiceImpl matchServiceImpl;

    public MatchController(MatchServiceImpl matchServiceImpl) {
        this.matchServiceImpl = matchServiceImpl;
    }

    @PostMapping("/create")
    public Match createMatch(@RequestBody MatchDto matchDto) throws Exception{
        return matchServiceImpl.createMatch(matchDto);
    }

}
