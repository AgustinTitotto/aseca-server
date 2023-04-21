package com.example.asecaserver.team;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class TeamRepositoryTest {

    @Autowired
    private TeamRepository teamRepository;

    @Test
    void shouldCheckIfTeamExists(){
        Team team = new Team("Mavericks");
        teamRepository.save(team);
        assertThat(team.getId()).isEqualTo(teamRepository.findByTeamName(team.getTeamName()).getId());
    }

}