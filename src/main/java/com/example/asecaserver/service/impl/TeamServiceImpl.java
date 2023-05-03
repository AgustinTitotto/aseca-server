package com.example.asecaserver.service.impl;

import com.example.asecaserver.model.Team;
import com.example.asecaserver.repository.TeamRepository;
import com.example.asecaserver.service.TeamService;
import com.example.asecaserver.validator.TeamValidator;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository repository;
    private TeamValidator validator;

    public TeamServiceImpl(TeamRepository repository) {
        this.repository = repository;
    }

    @Override
    public Team findById(Long id) throws Exception{
        return repository.findById(id).orElseThrow(() -> new Exception("No team exists with id: " + id));
    }

    public Team insert(Team team) {
        // needs validation
        return repository.save(team);
    }

    public Team update(Team team){
        // needs validation
        return team;
    }

    public void saveTeams(List<Team> teams) {
        repository.saveAll(teams);
    }
}
