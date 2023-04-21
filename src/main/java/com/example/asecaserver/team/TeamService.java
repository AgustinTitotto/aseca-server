package com.example.asecaserver.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TeamService {

    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public void createTeam(Team team) {
        teamRepository.save(team);
    }

    public void getTeam(Team team) {
        teamRepository.findByTeamName(team.getTeamName());
    }
}
