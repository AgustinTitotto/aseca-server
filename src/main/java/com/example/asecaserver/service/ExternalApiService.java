package com.example.asecaserver.service;

import com.example.asecaserver.model.Team;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;

public interface ExternalApiService {

    void createMatches(List<Team> teams, Date startDate, Date finishDate, Long leagueId) throws Exception;
    void validateDates(Date startDate, Date finishDate) throws Exception;
    HttpURLConnection createConnection(URL url) throws IOException;
}
