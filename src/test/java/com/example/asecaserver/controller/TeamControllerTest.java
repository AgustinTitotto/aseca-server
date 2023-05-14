package com.example.asecaserver.controller;

import com.example.asecaserver.model.Player;
import com.example.asecaserver.model.Team;
import com.example.asecaserver.repository.TeamRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TeamControllerTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    @Autowired
    private TeamRepository teamRepository;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(String.valueOf(port)).concat("/team");
    }


    @Test
    @DirtiesContext
    void shouldFindTeamById() {
        //given
        Team team = new Team("Mavericks");
        team.setId(1L);
        teamRepository.save(team);
        //when
        ResponseEntity<Team> response = restTemplate.getForEntity(baseUrl + "/get?id=" + "{id}", Team.class, 1L);
        Team responseTeam = response.getBody();
        //then
        assert responseTeam != null;
        assertThat(responseTeam.getId()).isEqualTo(team.getId());
        assertThat(responseTeam.getTeamName()).isEqualTo(team.getTeamName());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DirtiesContext
    void shouldGetTeamPlayers() {
        //given
        List<Player> playerList = Arrays.asList(new Player("A"), new Player("B"), new Player("C"));
        Team team = new Team("Mavericks");
        team.setId(1L);
        team.setPlayers(playerList);
        teamRepository.save(team);
        //when
        ResponseEntity<List<Player>> response = restTemplate.exchange(baseUrl + "/getPlayers?teamId=" + "{id}", HttpMethod.GET, null, new ParameterizedTypeReference<List<Player>>() {}, 1L);
        List<Player> responseTeams = response.getBody();
        //then
        assert responseTeams != null;
        assertThat(responseTeams.size()).isEqualTo(3);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }
}