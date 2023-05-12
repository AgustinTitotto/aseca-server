package com.example.asecaserver.service.impl;

import com.example.asecaserver.model.*;
import com.example.asecaserver.model.dtos.MatchDto;
import com.example.asecaserver.model.dtos.PointDto;
import com.example.asecaserver.repository.*;
import com.example.asecaserver.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.ObjectProvider;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatchServiceImplTest {

    @Mock
    private MatchRepository matchRepository;
    @Mock
    private ObjectProvider<LeagueService> leagueServiceObjectProvider;
    @Mock
    private LeagueService leagueService;
    @Mock
    private TeamService teamService;
    @Mock
    private TeamStatService teamStatService;
    @Mock
    private PlayerStatService playerStatService;

    private MatchService underTest;

    @BeforeEach
    void setUp() {
        underTest = new MatchServiceImpl(matchRepository, leagueServiceObjectProvider, teamService, teamStatService, playerStatService);
    }

    @Test
    void shouldFindMatchById() throws Exception {
        //given
        Long id = 1L;
        Match match = new Match();
        when(matchRepository.findById(id)).thenReturn(Optional.of(match));
        //when
        underTest.findById(id);
        //then
        verify(matchRepository).findById(id);
    }

    @Test
    void shouldCheckForExistingMatchAndSaveMatch() throws Exception {
        //given
        League league = new League();
        Long id1 = 1L;
        league.setId(id1);
        Team team1 = new Team();
        Team team2 = new Team();
        Long team1Id = 1L;
        Long team2Id = 2L;
        team1.setId(team1Id);
        team2.setId(team2Id);
        MatchDto matchDto = new MatchDto(new Date(), team1.getId(), team2.getId(), league.getId());
        when(matchRepository.findByLocalTeamIdAndAwayTeamIdAndLeagueId(team1.getId(), team2.getId(), league.getId())).thenReturn(Optional.empty());
        when(teamService.findById(team1.getId())).thenReturn(team1);
        when(teamService.findById(team2.getId())).thenReturn(team2);
        when(leagueServiceObjectProvider.getIfAvailable()).thenReturn(leagueService);
        when(leagueService.findById(league.getId())).thenReturn(league);
        //when
        underTest.createMatch(matchDto);
        //then
        verify(matchRepository).findByLocalTeamIdAndAwayTeamIdAndLeagueId(team1.getId(), team2.getId(), league.getId());
        ArgumentCaptor<Match> matchArgumentCaptor = ArgumentCaptor.forClass(Match.class);
        verify(matchRepository).save(matchArgumentCaptor.capture());
        assertThat(matchArgumentCaptor.getValue().getLocalTeam().getId()).isEqualTo(matchDto.getLocalTeamId());
        assertThat(matchArgumentCaptor.getValue().getAwayTeam().getId()).isEqualTo(matchDto.getAwayTeamId());
        assertThat(matchArgumentCaptor.getValue().getLeague().getId()).isEqualTo(matchDto.getLeagueId());
    }

    @Test
    void shouldAddPointsAndPlayerStats() throws Exception {
        //given
        League league = new League();
        Long id1 = 1L;
        league.setId(id1);
        Team team1 = new Team();
        Team team2 = new Team();
        Long team1Id = 1L;
        Long team2Id = 2L;
        team1.setId(team1Id);
        team2.setId(team2Id);
        Match match = new Match(team1, team2, league);
        match.setId(1L);
        match.setHasEnded(false);
        PointDto pointDto1 = new PointDto(match.getId(), match.getLocalTeam().getId(), 1L, 2, 2L);
        when(matchRepository.findById(match.getId())).thenReturn(Optional.of(match));
        //when
        underTest.addPoint(pointDto1);
        //then
        verify(playerStatService).addStatsToPlayer(1L, 2, 2L, match.getLeague());
        ArgumentCaptor<Match> argumentCaptor = ArgumentCaptor.forClass(Match.class);
        verify(matchRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getLocalScore()).isEqualTo(2);
    }

    @Test
    void shouldEndMatchAndAddTeamStat() throws Exception {
        //given
        League league = new League();
        Long id1 = 1L;
        league.setId(id1);
        Team team1 = new Team();
        Team team2 = new Team();
        Long team1Id = 1L;
        Long team2Id = 2L;
        team1.setId(team1Id);
        team2.setId(team2Id);
        Match match = new Match(team1, team2, league);
        match.setId(1L);
        match.setHasEnded(false);
        when(matchRepository.findById(match.getId())).thenReturn(Optional.of(match));
        when(teamStatService.getStatByLeagueIdAndTeamId(league.getId(), team1.getId())).thenThrow(new Exception());
        when(teamStatService.getStatByLeagueIdAndTeamId(league.getId(), team2.getId())).thenThrow(new Exception());
        //when
        underTest.endMatch(match.getId(), 26, 30);
        //then
        ArgumentCaptor<Match> argumentCaptor = ArgumentCaptor.forClass(Match.class);
        verify(matchRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().hasEnded()).isEqualTo(true);
        ArgumentCaptor<TeamStat> argumentCaptor1 = ArgumentCaptor.forClass(TeamStat.class);
        verify(teamStatService, times(2)).saveStat(argumentCaptor1.capture());
        assertThat(argumentCaptor1.getAllValues().get(0).getMatchesPlayed()).isEqualTo(1);
        assertThat(argumentCaptor1.getAllValues().get(0).getWins()).isEqualTo(0);
        assertThat(argumentCaptor1.getAllValues().get(0).getLosses()).isEqualTo(1);
        assertThat(argumentCaptor1.getAllValues().get(0).getPointInFavour()).isEqualTo(-4);
        assertThat(argumentCaptor1.getAllValues().get(0).getWinStreak()).isEqualTo(0);
        assertThat(argumentCaptor1.getAllValues().get(1).getMatchesPlayed()).isEqualTo(1);
        assertThat(argumentCaptor1.getAllValues().get(1).getWins()).isEqualTo(1);
        assertThat(argumentCaptor1.getAllValues().get(1).getLosses()).isEqualTo(0);
        assertThat(argumentCaptor1.getAllValues().get(1).getPointInFavour()).isEqualTo(4);
        assertThat(argumentCaptor1.getAllValues().get(1).getWinStreak()).isEqualTo(1);
    }
}