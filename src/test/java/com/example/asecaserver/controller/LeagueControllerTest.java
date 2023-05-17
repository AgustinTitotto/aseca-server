package com.example.asecaserver.controller;

import com.example.asecaserver.model.League;
import com.example.asecaserver.model.Team;
import com.example.asecaserver.model.dtos.CreateLeagueDto;
import com.example.asecaserver.repository.LeagueRepository;
import com.example.asecaserver.repository.PlayerStatRepository;
import com.example.asecaserver.repository.TeamStatRepository;
import com.example.asecaserver.service.impl.ExternalApiServiceImpl;
import com.github.tomakehurst.wiremock.WireMockServer;
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

import java.util.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LeagueControllerTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    @Autowired
    private LeagueRepository leagueRepository;
    @Autowired
    private TeamStatRepository teamStatRepository;
    @Autowired
    private PlayerStatRepository playerStatRepository;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(String.valueOf(port)).concat("/league");
    }

    @Test
    @DirtiesContext
    void shouldGetLeague() {
        //given
        League league = new League("NBA");
        league.setId(1L);
        League league1 = new League("ABN");
        league1.setId(2L);
        leagueRepository.save(league);
        leagueRepository.save(league1);
        //when
        ResponseEntity<League> response = restTemplate.getForEntity(baseUrl + "?id=" + "{id}", League.class, 1L);
        League responseLeague = response.getBody();
        //then
        assert responseLeague != null;
        assertThat(responseLeague.getLeagueName()).isEqualTo(league.getLeagueName());
        assertThat(responseLeague.getId()).isEqualTo(league.getId());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DirtiesContext
    void shouldGetAllLeagues() {
        //given
        League league = new League("NBA");
        league.setId(1L);
        League league1 = new League("ABN");
        league1.setId(2L);
        leagueRepository.save(league);
        leagueRepository.save(league1);
        //when
        ResponseEntity<List<League>> response = restTemplate.exchange(baseUrl + "/all", HttpMethod.GET, null, new ParameterizedTypeReference<List<League>>(){});
        List<League> responseLeagues = response.getBody();
        //then
        assert responseLeagues != null;
        assertThat(responseLeagues.size()).isEqualTo(2);
        assertThat(responseLeagues.get(0).getId()).isEqualTo(league.getId());
        assertThat(responseLeagues.get(1).getId()).isEqualTo(league1.getId());
        assertThat(responseLeagues.get(0).getLeagueName()).isEqualTo(league.getLeagueName());
        assertThat(responseLeagues.get(1).getLeagueName()).isEqualTo(league1.getLeagueName());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private WireMockServer wireMockServer;

    @Test
    void shouldCreateLeague() {
        //given
        List<String> teamNames = Arrays.asList("Mavericks", "Bulls");
        CreateLeagueDto createLeagueDto = new CreateLeagueDto("NBA", teamNames, new Date(2050, Calendar.JUNE, 1), new Date(2050, Calendar.JULY, 1));
        String responseBody = "[{\"gameDay\":1,\"date\":{\"min\":0,\"hrs\":4,\"day\":1,\"month\":6,\"year\":2050},\"homeTeamId\":2,\"awayTeamId\":1},{\"gameDay\":2,\"date\":{\"min\":0,\"hrs\":4,\"day\":1,\"month\":7,\"year\":2050},\"homeTeamId\":1,\"awayTeamId\":2}]";
        wireMockServer = new WireMockServer(8081);  // Choose a port for the mock server
        wireMockServer.start();

        configureFor("localhost", 8081);

        // Define the expected behavior for the mock server
        stubFor(post(urlEqualTo("/fixture"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));

        //when
        ResponseEntity<League> response = restTemplate.postForEntity(baseUrl + "/add", createLeagueDto, League.class);
        League responseLeague = response.getBody();
        //then
        assert responseLeague != null;
        assertThat(responseLeague.getLeagueName()).isEqualTo(createLeagueDto.getLeagueName());
        assertThat(responseLeague.getTeams().size()).isEqualTo(2);
        assertThat(responseLeague.getTeams().get(0).getPlayers().size()).isEqualTo(12);
        assertThat(teamStatRepository.findAllByLeagueId(responseLeague.getId()).size()).isEqualTo(2);
        assertThat(playerStatRepository.findAll().size()).isEqualTo(24);
        wireMockServer.stop();
    }

    @Test
    @DirtiesContext
    void shouldGetLeagueTeams() {
        //given
        League league = new League("NBA");
        league.setId(1L);
        league.setTeams(Arrays.asList(new Team("Mavericks"), new Team("Bulls")));
        leagueRepository.save(league);
        //when
        ResponseEntity<List<Team>> response = restTemplate.exchange(baseUrl + "/getTeams?leagueId=" + "{id}", HttpMethod.GET, null, new ParameterizedTypeReference<List<Team>>(){}, 1L);
        List<Team> responseLeagues = response.getBody();
        //then
        assert responseLeagues != null;
        assertThat(responseLeagues.size()).isEqualTo(2);
        assertThat(responseLeagues.get(0).getTeamName()).isEqualTo("Mavericks");
        assertThat(responseLeagues.get(1).getTeamName()).isEqualTo("Bulls");
    }
}