package com.example.asecaserver.service.impl;


import com.example.asecaserver.model.Team;
import com.example.asecaserver.service.ExternalApiService;
import com.example.asecaserver.service.MatchService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ExternalApiServiceImpl implements ExternalApiService {

    private final MatchService matchService;

    public ExternalApiServiceImpl(MatchService matchService) {
        this.matchService = matchService;
    }


    @Override
    public void createMatches(List<Team> teams, Date startDate, Date finishDate, Long leagueId) throws Exception {
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar finish = Calendar.getInstance();
        finish.setTime(finishDate);
        URL url = new URL("http://external-api:8081/fixture");
        HttpURLConnection con = createConnection(url);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        //Set RequestBody
        StringBuilder jsonInputString = new StringBuilder("{\"startDate\":" + "\"" + start.get(Calendar.YEAR) + "-" + String.format("%02d", start.get(Calendar.MONTH) + 1) + "-" + String.format("%02d", start.get(Calendar.DATE)) + "\"" + ",\"endDate\":" + "\"" + finish.get(Calendar.YEAR) + "-" + String.format("%02d", finish.get(Calendar.MONTH) + 1) + "-" + String.format("%02d", finish.get(Calendar.DATE)) + "\"" + ",\"teamsId\":[");
        for (int i = 0; i < teams.size() - 1; i++) {
            jsonInputString.append(teams.get(i).getId().toString()).append(",");
        }
        jsonInputString.append(teams.get(teams.size() - 1).getId().toString()).append("]}");

        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.toString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        //Read Response
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            Objects.requireNonNull(matchService).createMatches(response.toString(), leagueId);
        }
    }

    @Override
    public HttpURLConnection createConnection(URL url) throws IOException {
        return (HttpURLConnection) url.openConnection();
    }

}
