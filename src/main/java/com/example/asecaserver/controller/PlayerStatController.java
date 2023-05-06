package com.example.asecaserver.controller;


import com.example.asecaserver.model.PlayerStat;
import com.example.asecaserver.service.impl.PlayerStatServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/playerStat")
public class PlayerStatController {

    private final PlayerStatServiceImpl playerStatService;

    public PlayerStatController(PlayerStatServiceImpl playerStatService) {
        this.playerStatService = playerStatService;
    }

    @GetMapping("/getStat")
    public PlayerStat getPlayerStat(@RequestBody Long playerId) throws Exception {
        return playerStatService.getPlayerStatById(playerId);
    }
}
