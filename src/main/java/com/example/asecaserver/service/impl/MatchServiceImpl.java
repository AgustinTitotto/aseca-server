package com.example.asecaserver.service.impl;

import com.example.asecaserver.model.*;
import com.example.asecaserver.model.dtos.MatchDateDto;
import com.example.asecaserver.model.dtos.MatchDto;
import com.example.asecaserver.model.dtos.PointDto;
import com.example.asecaserver.repository.MatchRepository;
import com.example.asecaserver.service.LeagueService;
import com.example.asecaserver.service.MatchService;
import com.example.asecaserver.service.PlayerStatService;
import com.example.asecaserver.service.TeamService;
import com.example.asecaserver.service.TeamStatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.Map;

@Service
public class MatchServiceImpl implements MatchService {

    private final MatchRepository repository;
    private final LeagueService leagueService;
    private final TeamService teamService;
    private final TeamStatService teamStatService;
    private final PlayerStatService playerStatService;

    public MatchServiceImpl(MatchRepository repository, LeagueService leagueService, TeamService teamService, TeamStatService teamStatService, PlayerStatService playerStatService) {
        this.repository = repository;
        this.leagueService = leagueService;
        this.teamService = teamService;
        this.teamStatService = teamStatService;
        this.playerStatService = playerStatService;
    }

    public Match findById(Long id) throws Exception {
        return repository.findById(id).orElseThrow(() -> new Exception("No match exists with id: " + id));
    }

    public Match createMatch(MatchDto matchDto) throws Exception {
        Match match = new Match();
        match.setLocalTeam(teamService.findById(matchDto.getLocalTeamId()));
        match.setAwayTeam(teamService.findById(matchDto.getAwayTeamId()));
        match.setLeague(leagueService.findById(matchDto.getLeagueId()));
        match.setDate(matchDto.getDate());
        if (matchAlreadyExists(match)) throw new Exception("This match already exists on this league");
        return repository.save(match);
    }

    private boolean matchAlreadyExists(Match match) {
        return repository.findByLocalTeamIdAndAwayTeamIdAndLeagueId(
                    match.getLocalTeam().getId(),
                match.getAwayTeam().getId(),
                match.getLeague().getId()
        ).isPresent();
    }

    //add 1 game in local and away teams stats in this league
    //add win and loss
    //set winStreak
    //set points in favour
    public void endMatch(Long matchId, Integer localScore, Integer awayScore) throws Exception {
        Match match = findById(matchId);
        if (!match.hasEnded()) {
            League league = match.getLeague();
            Team local = match.getLocalTeam();
            Team away = match.getAwayTeam();
            TeamStat localStats = getTeamStat(league, local);
            TeamStat awayStats = getTeamStat(league, away);

            match.setHasEnded(true);
            repository.save(match);

            addOneGameToTeams(localStats, awayStats);
            setWinLossAndWinStreak(localScore, awayScore, localStats, awayStats);
            setPointInFavour(localScore, awayScore, localStats, awayStats);
            setWinPercentage(localStats, awayStats);

            teamStatService.saveStat(localStats);
            teamStatService.saveStat(awayStats);
        }
        else throw new Exception("Match does not exist or has already ended");
    }

    public void addPoint(PointDto point) throws Exception {
        Match match = findById(point.getMatchId());
        if (!match.hasEnded()) {
            League league = match.getLeague();
            //Change match score
            if (point.getTeamId().equals(match.getLocalTeam().getId())) {
                match.setLocalScore(match.getLocalScore() + point.getScore());
            } else if (point.getTeamId().equals(match.getAwayTeam().getId())) {
                match.setAwayScore(match.getAwayScore() + point.getScore());
            } else throw new Exception("Team that scored is not in match");
            //Set player stat (score and assist)
            playerStatService.addStatsToPlayer(point.getScoringPlayerId(), point.getScore(), point.getAssistPlayerId(), league);
            repository.save(match);
        }
        else throw new Exception("Match does not exist or has already ended");
    }

    private static void setWinPercentage(TeamStat localStats, TeamStat awayStats) {
        localStats.setWinPercentage((double) (localStats.getWins()/(localStats.getMatchesPlayed())));
        awayStats.setWinPercentage((double) (awayStats.getWins()/(awayStats.getMatchesPlayed())));
    }

    private static void setPointInFavour(Integer localScore, Integer awayScore, TeamStat localStats, TeamStat awayStats) {
        localStats.setPointInFavour(localStats.getPointInFavour() + (localScore - awayScore));
        awayStats.setPointInFavour(awayStats.getPointInFavour() + (awayScore - localScore));
    }

    private static void addOneGameToTeams(TeamStat localStats, TeamStat awayStats) {
        localStats.setMatchesPlayed(localStats.getMatchesPlayed() + 1);
        awayStats.setMatchesPlayed(awayStats.getMatchesPlayed() + 1);
    }

    private static void setWinLossAndWinStreak(Integer localScore, Integer awayScore, TeamStat localStats, TeamStat awayStats) {
        if (localScore > awayScore) {
            localStats.setWins(localStats.getWins() + 1);
            localStats.setWinStreak(localStats.getWinStreak() + 1);

            awayStats.setLosses(awayStats.getLosses() + 1);
            awayStats.setWinStreak(0);
        }
        else {
            localStats.setLosses(localStats.getLosses() + 1);
            localStats.setWinStreak(0);

            awayStats.setWins(awayStats.getWins() + 1);
            awayStats.setWinStreak(awayStats.getWinStreak() + 1);
        }
    }

    private TeamStat getTeamStat(League league, Team local) {
        TeamStat localStats;
        try {
            localStats = teamStatService.getStatByLeagueIdAndTeamId(league.getId(), local.getId());
        } catch (Exception e) {
            localStats = new TeamStat();
            localStats.setTeam(local);
            localStats.setLeague(league);
        }
        return localStats;
    }

    public List<Match> getLeagueMatches(Long leagueId) {
        return repository.getByLeagueId(leagueId);
    }

    @Override
    public void createMatches(String responseLine, Long leagueId) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<MatchDateDto> jsonMap = objectMapper.readValue(responseLine, new TypeReference<List<MatchDateDto>>(){});
        for (MatchDateDto matchDto : jsonMap) {
            createMatch(new MatchDto(matchDto.getGameDay(), matchDto.getDate(), matchDto.getHomeTeamId(), matchDto.getAwayTeamId(), leagueId));
        }
//        for (Map.Entry<String,Object> entry : jsonMap.entrySet()) {
//           // createMatch(new MatchDto(entry.ge, entry.getValue().getDate(), entry.getValue().getLocalTeamId(), entry.getValue().getAwayTeamId(), leagueId));
//        }
    }
}
