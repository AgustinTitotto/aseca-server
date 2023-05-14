package com.example.asecaserver.controller;

import com.example.asecaserver.model.*;
import com.example.asecaserver.model.dtos.EndMatchDto;
import com.example.asecaserver.model.dtos.PointDto;
import com.example.asecaserver.repository.*;
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
class MatchControllerTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private LeagueRepository leagueRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private PlayerStatRepository playerStatRepository;
    @Autowired
    private TeamStatRepository teamStatRepository;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(String.valueOf(port)).concat("/match");
    }

    @Test
    @DirtiesContext
    void shouldAddPointAndPlayerStat() {
        //given
        League league = new League("NBA");
        league.setId(1L);
        Team team1 = new Team("Mav");
        team1.setId(1L);
        Team team2 = new Team("Bull");
        team2.setId(2L);
        teamRepository.saveAll(Arrays.asList(team1, team2));
        leagueRepository.save(league);
        Player scoring = new Player("A");
        Player assisting = new Player("B");
        playerRepository.saveAll(Arrays.asList(scoring, assisting));
        Match match = new Match();
        match.setId(1L);
        match.setLocalTeam(team1);
        match.setAwayTeam(team2);
        match.setLeague(league);
        matchRepository.save(match);
        PointDto pointDto = new PointDto(1L, 1L, 1L, 3, 2L);
        //when
        ResponseEntity<Match> response = restTemplate.postForEntity(baseUrl + "/addPoint", pointDto, Match.class);
        Match responseMatch = response.getBody();
        //then
        assert responseMatch != null;
        assertThat(responseMatch.hasEnded()).isFalse();
        assertThat(responseMatch.getLocalScore()).isEqualTo(3);
        assertThat(responseMatch.getAwayScore()).isEqualTo(0);
        assertThat(playerStatRepository.findByPlayerId(1L).get().getPoints3()).isEqualTo(1);
        assertThat(playerStatRepository.findByPlayerId(1L).get().getPointsScored()).isEqualTo(3);
        assertThat(playerStatRepository.findByPlayerId(2L).get().getAssists()).isEqualTo(1);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DirtiesContext
    void shouldEndMatchAndSaveTeamStat() {
        //given
        League league = new League("NBA");
        league.setId(1L);
        Team team1 = new Team("Mav");
        team1.setId(1L);
        Team team2 = new Team("Bull");
        team2.setId(2L);
        teamRepository.saveAll(Arrays.asList(team1, team2));
        leagueRepository.save(league);
        Match match = new Match();
        match.setId(1L);
        match.setLocalTeam(team1);
        match.setAwayTeam(team2);
        match.setLeague(league);
        match.setLocalScore(30);
        match.setAwayScore(20);
        matchRepository.save(match);
        EndMatchDto endMatchDto = new EndMatchDto(1L, 30, 20);
        //when
        ResponseEntity<Match> response = restTemplate.postForEntity(baseUrl + "/end", endMatchDto, Match.class);
        //then
        assert matchRepository.findById(1L).isPresent();
        assertThat(matchRepository.findById(1L).get().hasEnded()).isTrue();
        assert teamStatRepository.findById(1L).isPresent();
        assertThat(teamStatRepository.findById(1L).get().getMatchesPlayed()).isEqualTo(1);
        assertThat(teamStatRepository.findById(1L).get().getWins()).isEqualTo(1);
        assertThat(teamStatRepository.findById(1L).get().getPointInFavour()).isEqualTo(10);
        assertThat(teamStatRepository.findById(1L).get().getWinStreak()).isEqualTo(1);
        assertThat(teamStatRepository.findById(1L).get().getWinPercentage()).isEqualTo(1.0);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DirtiesContext
    void shouldGetLeagueMatches() {
        //given
        League league = new League("NBA");
        league.setId(1L);
        Team team1 = new Team("Mav");
        team1.setId(1L);
        Team team2 = new Team("Bull");
        team2.setId(2L);
        teamRepository.saveAll(Arrays.asList(team1, team2));
        leagueRepository.save(league);
        Match match1 = new Match();
        match1.setId(1L);
        match1.setLocalTeam(team1);
        match1.setAwayTeam(team2);
        match1.setLeague(league);
        match1.setLocalScore(30);
        match1.setAwayScore(20);
        Match match2 = new Match();
        match2.setId(2L);
        match2.setLocalTeam(team2);
        match2.setAwayTeam(team1);
        match2.setLeague(league);
        match2.setLocalScore(26);
        match2.setAwayScore(13);
        matchRepository.saveAll(Arrays.asList(match1, match2));
        //when
        ResponseEntity<List<Match>> response = restTemplate.exchange(baseUrl + "/leagueMatches?leagueId=" + "{id}", HttpMethod.GET, null, new ParameterizedTypeReference<List<Match>>() {}, 1L);
        List<Match> responseMatches = response.getBody();
        //then
        assert responseMatches != null;
        assertThat(responseMatches.size()).isEqualTo(2);
        assertThat(responseMatches.get(0).getLocalScore()).isEqualTo(30);
        assertThat(responseMatches.get(0).getAwayScore()).isEqualTo(20);
        assertThat(responseMatches.get(1).getLocalScore()).isEqualTo(26);
        assertThat(responseMatches.get(1).getAwayScore()).isEqualTo(13);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}