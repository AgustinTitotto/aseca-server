package com.example.asecaserver.service.impl;

import com.example.asecaserver.model.*;
import com.example.asecaserver.model.dtos.MatchDto;
import com.example.asecaserver.model.dtos.PointDto;
import com.example.asecaserver.repository.MatchRepository;
import com.example.asecaserver.service.MatchService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MatchServiceImpl implements MatchService {

    private final MatchRepository repository;
    private final LeagueServiceImpl leagueService;
    private final TeamServiceImpl teamService;
    private final TeamStatServiceImpl statisticsService;
    private final PlayerStatServiceImpl playerStatService;

    public MatchServiceImpl(MatchRepository repository, LeagueServiceImpl leagueService, TeamServiceImpl teamService, TeamStatServiceImpl statisticsService, PlayerStatServiceImpl playerStatService) {
        this.repository = repository;
        this.leagueService = leagueService;
        this.teamService = teamService;
        this.statisticsService = statisticsService;
        this.playerStatService = playerStatService;
    }

    public Match createMatch(MatchDto matchDto) throws Exception {
        Match match = new Match();
        match.setLocalTeam(teamService.findById(matchDto.getLocalTeamId()));
        match.setAwayTeam(teamService.findById(matchDto.getAwayTeamId()));
        match.setLeague(leagueService.findById(matchDto.getLeagueId()));
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
        Optional<Match> match = repository.findById(matchId);
        if (match.isPresent() && !match.get().hasEnded()) {
            League league = match.get().getLeague();
            Team local = match.get().getLocalTeam();
            Team away = match.get().getAwayTeam();
            TeamStat localStats = getTeamStat(league, local);
            TeamStat awayStats = getTeamStat(league, away);

            match.get().setHasEnded(true);
            repository.save(match.get());

            addOneGameToTeams(localStats, awayStats);
            setWinLossAndWinStreak(localScore, awayScore, localStats, awayStats);
            setPointInFavour(localScore, awayScore, localStats, awayStats);
            setWinPercentage(localStats, awayStats);

            statisticsService.saveStat(localStats);
            statisticsService.saveStat(awayStats);
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

        if (statisticsService.getStatByLeagueIdAndTeamId(league.getId(), local.getId()).isEmpty()) {
            localStats = new TeamStat();
            localStats.setTeam(local);
            localStats.setLeague(league);
        }
        else {
            localStats = statisticsService.getStatByLeagueIdAndTeamId(league.getId(), local.getId()).get();
        }
        return localStats;
    }

    public void addPoint(PointDto point) throws Exception {
        Optional<Match> match = repository.findById(point.getMatchId());
        if (match.isPresent() && !match.get().hasEnded()) {
            League league = match.get().getLeague();
            //Change match score
            if (point.getTeamId().longValue() == match.get().getLocalTeam().getId().longValue()) {
                match.get().setLocalScore(match.get().getLocalScore() + point.getScore());
            } else if (point.getTeamId().longValue() == match.get().getLocalTeam().getId().longValue()) {
                match.get().setAwayScore(match.get().getAwayScore() + point.getScore());
            } else throw new Exception("Team that scored is not in match");
            //Set player stat (score and assist)
            playerStatService.addStatsToPlayers(point.getScoringPlayerId(), point.getScore(), point.getAssistPlayerId(), league);
            repository.save(match.get());
        }
        else throw new Exception("Match does not exist or has already ended");
    }
}
