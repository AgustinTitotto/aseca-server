package com.example.asecaserver.service.impl;

import com.example.asecaserver.model.League;
import com.example.asecaserver.model.Player;
import com.example.asecaserver.repository.LeagueRepository;
import com.example.asecaserver.model.Team;
import com.example.asecaserver.service.LeagueService;
import com.example.asecaserver.service.MatchService;
import com.example.asecaserver.service.PlayerService;
import com.example.asecaserver.service.TeamService;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class LeagueServiceImpl implements LeagueService {

    private final LeagueRepository repository;
    private final TeamService teamService;
    private final PlayerService playerService;
    private final ObjectProvider<MatchService> matchService;


    public LeagueServiceImpl(LeagueRepository repository, TeamService teamService, PlayerService playerService, ObjectProvider<MatchService> matchService) {
        this.repository = repository;
        this.teamService = teamService;
        this.playerService = playerService;
        this.matchService = matchService;
    }

    public List<League> findAll() {
        return repository.findAll();
    }

    public League findById(Long id) throws Exception {
        return repository.findById(id).orElseThrow(() -> new Exception("No league exists with id: " + id));
    }

    public League addLeague(String leagueName, List<String> teamNames, Date startDate, Date finishDate) throws Exception {
        List<Team> teams = saveTeamAndPlayer(teamNames);
        League league = new League(leagueName);
        league.setTeams(teams);
        League savedLeague =  repository.save(league);
        createMatches(teams, startDate, finishDate, savedLeague.getId());
        return savedLeague;
    }

    private void createMatches(List<Team> teams, Date startDate, Date finishDate, Long leagueId) throws Exception {
        Date date = new Date();
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        startDate.setHours(1);
        if (startDate.before(date) || finishDate.before(startDate)) {
            throw new Exception("Invalid dates for creating league");
        }
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar finish = Calendar.getInstance();
        finish.setTime(finishDate);
        URL url = new URL("http://localhost:8081/fixture");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        String jsonInputString = "{\"startDate\":" + "\"" +start.get(Calendar.YEAR) + "-" + String.format("%02d", start.get(Calendar.MONTH) + 1) + "-" + String.format("%02d", start.get(Calendar.DATE)) + "\"" +",\"endDate\":" + "\"" + finish.get(Calendar.YEAR) + "-" + String.format("%02d", finish.get(Calendar.MONTH) + 1) + "-" + String.format("%02d", finish.get(Calendar.DATE)) + "\"" +",\"teamsId\":[";
        for (int i = 0; i < teams.size() - 1; i++) {
            jsonInputString = jsonInputString + teams.get(i).getId().toString() + ",";
        }

        jsonInputString = jsonInputString + teams.get(teams.size()-1).getId().toString() + "]}";
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            matchService.getIfAvailable().createMatches(response.toString(), leagueId);
            System.out.println(response.toString());
        }
    }

    private List<Team> saveTeamAndPlayer(List<String> teamNames) {
        List<Team> teams = new ArrayList<>();
        int playerPerTeam = 12;
        for (String teamName : teamNames) {
            Team team = new Team(teamName);
            List<Player> players = new ArrayList<>();
            for (int i = 0; i < playerPerTeam; i++) {
                Faker faker = new Faker();
                Player player = new Player(faker.name().fullName());
                players.add(player);
                playerService.savePlayer(player);
            }
            teams.add(team);
            team.setPlayers(players);
            teamService.saveTeam(team);
        }
        return teams;
    }

    public List<Team> getTeams(Long leagueId) throws Exception {
        League league = findById(leagueId);
        return league.getTeams();
    }

}
