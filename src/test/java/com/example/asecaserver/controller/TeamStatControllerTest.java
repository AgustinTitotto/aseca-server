package com.example.asecaserver.controller;

import com.example.asecaserver.model.League;
import com.example.asecaserver.model.Team;
import com.example.asecaserver.model.TeamStat;
import com.example.asecaserver.repository.LeagueRepository;
import com.example.asecaserver.repository.TeamStatRepository;
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
class TeamStatControllerTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    @Autowired
    private TeamStatRepository teamStatRepository;
    @Autowired
    private LeagueRepository leagueRepository;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(String.valueOf(port)).concat("/leagueTable");
    }

    @Test
    @DirtiesContext
    void shouldGetLeagueTable() {
        //given
        Team team1 = new Team("A");
        League league = new League("NBA");
        league.setId(1L);
        leagueRepository.save(league);
        TeamStat teamStat1 = new TeamStat();
        TeamStat teamStat2 = new TeamStat();
        TeamStat teamStat3 = new TeamStat();
        TeamStat teamStat4 = new TeamStat();
        teamStat1.setLeague(league);
        teamStat2.setLeague(league);
        teamStat3.setLeague(league);
        teamStat4.setLeague(league);
        teamStatRepository.saveAll(Arrays.asList(teamStat1, teamStat2, teamStat3, teamStat4));
        //when
        ResponseEntity<List<TeamStat>> response = restTemplate.exchange(baseUrl + "/get?leagueId=" + "{id}", HttpMethod.GET, null, new ParameterizedTypeReference<List<TeamStat>>() {}, 1L);
        List<TeamStat> responseLeagueTable = response.getBody();
        //then
        assert responseLeagueTable != null;
        assertThat(responseLeagueTable.size()).isEqualTo(4);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}