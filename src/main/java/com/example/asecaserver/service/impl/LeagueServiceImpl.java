package com.example.asecaserver.service.impl;

import com.example.asecaserver.model.League;
import com.example.asecaserver.repository.LeagueRepository;
import com.example.asecaserver.model.Team;
import com.example.asecaserver.service.LeagueService;
import com.example.asecaserver.service.MatchService;
import com.example.asecaserver.service.TeamService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class LeagueServiceImpl implements LeagueService {

    private final LeagueRepository repository;
    private final TeamService teamService;
    private final ObjectProvider<MatchService> matchService;


    public LeagueServiceImpl(LeagueRepository repository, TeamService teamService, ObjectProvider<MatchService> matchService) {
        this.repository = repository;
        this.teamService = teamService;
        this.matchService = matchService;
    }

    public List<League> findAll() {
        return repository.findAll();
    }

    public League findById(Long id) throws Exception {
        return repository.findById(id).orElseThrow(() -> new Exception("No league exists with id: " + id));
    }

    public League addLeague(String leagueName, List<String> teamNames, Date startDate, Date finishDate) throws Exception {
        List<Team> teams = teamService.saveTeamsAndPlayer(teamNames);
        League league = new League(leagueName);
        league.setTeams(teams);
        League savedLeague =  repository.save(league);
        createMatches(teams, startDate, finishDate, savedLeague.getId());
        return savedLeague;
    }

    private void createMatches(List<Team> teams, Date startDate, Date finishDate, Long leagueId) throws Exception {
        validateDates(startDate, finishDate);
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar finish = Calendar.getInstance();
        finish.setTime(finishDate);
        URL url = new URL("http://localhost:8081/fixture");
        HttpURLConnection con = createConnection(url);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        StringBuilder jsonInputString = new StringBuilder("{\"startDate\":" + "\"" + start.get(Calendar.YEAR) + "-" + String.format("%02d", start.get(Calendar.MONTH) + 1) + "-" + String.format("%02d", start.get(Calendar.DATE)) + "\"" + ",\"endDate\":" + "\"" + finish.get(Calendar.YEAR) + "-" + String.format("%02d", finish.get(Calendar.MONTH) + 1) + "-" + String.format("%02d", finish.get(Calendar.DATE)) + "\"" + ",\"teamsId\":[");
        for (int i = 0; i < teams.size() - 1; i++) {
            jsonInputString.append(teams.get(i).getId().toString()).append(",");
        }
        jsonInputString.append(teams.get(teams.size() - 1).getId().toString()).append("]}");
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.toString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            Objects.requireNonNull(matchService.getIfAvailable()).createMatches(response.toString(), leagueId);
        }
    }

    private static void validateDates(Date startDate, Date finishDate) throws Exception {
        Date date = new Date();
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        startDate.setHours(1);
        if (startDate.before(date) || finishDate.before(startDate)) {
            throw new Exception("Invalid dates for creating league");
        }
    }

    private static HttpURLConnection createConnection(URL url) throws IOException {
        return (HttpURLConnection) url.openConnection();
    }

    public List<Team> getTeams(Long leagueId) throws Exception {
        League league = findById(leagueId);
        return league.getTeams();
    }

}
