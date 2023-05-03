package com.example.asecaserver.service.impl;

import com.example.asecaserver.model.TeamStat;
import com.example.asecaserver.repository.TeamStatRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamStatServiceImpl {

    private final TeamStatRepository repository;

    public TeamStatServiceImpl(TeamStatRepository repository) {
        this.repository = repository;
    }

    public Optional<TeamStat> getStatByLeagueIdAndTeamId(Long id, Long id1) {
        return repository.findByLeagueIdAndTeamId(id, id1);
    }

    public void saveStat(TeamStat localStats) {
        repository.save(localStats);
    }
}
