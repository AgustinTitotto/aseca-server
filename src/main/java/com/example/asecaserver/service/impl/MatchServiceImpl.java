package com.example.asecaserver.service.impl;

import com.example.asecaserver.model.*;
import com.example.asecaserver.model.dtos.MatchDto;
import com.example.asecaserver.repository.MatchRepository;
import com.example.asecaserver.service.MatchService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatchServiceImpl implements MatchService {

    private final MatchRepository repository;
    private final LeagueServiceImpl leagueService;
    private final TeamServiceImpl teamService;
    private final TeamStatServiceImpl statisticsService;

    public MatchServiceImpl(MatchRepository repository, LeagueServiceImpl leagueService, TeamServiceImpl teamService, TeamStatServiceImpl statisticsService) {
        this.repository = repository;
        this.leagueService = leagueService;
        this.teamService = teamService;
        this.statisticsService = statisticsService;
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
    public void endMatch(Long matchId, List<Point> points) throws Exception {
        Optional<Match> match = repository.findById(matchId);
        if (match.isPresent()) {
            League league = match.get().getLeague();
            Team local = match.get().getLocalTeam();
            Team away = match.get().getAwayTeam();
            TeamStat localStats;
            TeamStat awayStats;
            if (statisticsService.getStatByLeagueIdAndTeamId(league.getId(), local.getId()).isEmpty()) {
                localStats = new TeamStat();
                localStats.setTeam(local);
                localStats.setLeague(league);
            }
            else {
                localStats = statisticsService.getStatByLeagueIdAndTeamId(league.getId(), local.getId()).get();
            }
            if (statisticsService.getStatByLeagueIdAndTeamId(league.getId(), away.getId()).isEmpty()) {
                awayStats = new TeamStat();
                awayStats.setTeam(away);
                awayStats.setLeague(league);
            }
            else {
                awayStats = statisticsService.getStatByLeagueIdAndTeamId(league.getId(), away.getId()).get();
            }


            //add 1 game to each team
            localStats.setMatchesPlayed(localStats.getMatchesPlayed() + 1);
            awayStats.setMatchesPlayed(awayStats.getMatchesPlayed() + 1);

            int localPoints = 0;
            int awayPoints = 0;
            for (int i = 0; i < points.size(); i++) {
                if (points.get(i).getTeam().longValue() == local.getId().longValue()) {
                    localPoints = localPoints + points.get(i).getScore();
                }
                else if (points.get(i).getTeam().longValue() == away.getId().longValue()) {
                    awayPoints = awayPoints + points.get(i).getScore();
                }
                else throw new Exception("Team that scored is not in match");
            }
            match.get().setLocalScore(localPoints);
            match.get().setAwayScore(awayPoints);

            //set wins and losses and set winSteak (Depends on who wins)
            if (localPoints > awayPoints) {
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

            //Set point in favour
            localStats.setPointInFavour(localStats.getPointInFavour() + (localPoints - awayPoints));
            awayStats.setPointInFavour(awayStats.getPointInFavour() + (awayPoints - localPoints));

            localStats.setWinPercentage((double) (localStats.getWins()/(localStats.getMatchesPlayed())));
            awayStats.setWinPercentage((double) (awayStats.getWins()/(awayStats.getMatchesPlayed())));


            statisticsService.saveStat(localStats);
            statisticsService.saveStat(awayStats);

        }
        else throw new Exception("Match does not exist");
    }
}
