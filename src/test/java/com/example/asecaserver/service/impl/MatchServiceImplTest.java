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

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatchServiceImplTest {

    @Mock
    private MatchRepository matchRepository;
    @Mock
    private LeagueService leagueService;
    @Mock
    private TeamService teamService;
    @Mock
    private TeamStatService teamStatService;
    @Mock
    private PlayerStatService playerStatService;

    private MatchServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new MatchServiceImpl(matchRepository, leagueService, teamService, teamStatService, playerStatService);
    }

    @Test
    void shouldFindMatchById() throws Exception {
        //given
        Match match = new Match();
        when(matchRepository.findById(match.getId())).thenReturn(Optional.of(match));
        //when
        underTest.findById(match.getId());
        //then
        verify(matchRepository).findById(match.getId());
    }

    @Test
    void shouldCheckForTeamsAndLeagueAndSaveMatch() throws Exception {
        //given
        Long id1 = 1L;
        Long id2 = 2L;
        Long id3 = 3L;
        League league = new League();
        Team team1 = new Team();
        Team team2 = new Team();
        league.setId(id1);
        team1.setId(id2);
        team2.setId(id3);
        MatchDto matchDto = new MatchDto(team1.getId(), team2.getId(), league.getId());
        when(matchRepository.findByLocalTeamIdAndAwayTeamIdAndLeagueId(team1.getId(), team2.getId(), league.getId())).thenReturn(Optional.empty());
        when(teamService.findById(team1.getId())).thenReturn(team1);
        when(teamService.findById(team2.getId())).thenReturn(team2);
        when(leagueService.findById(league.getId())).thenReturn(league);
        //when
        underTest.createMatch(matchDto);
        //then
        ArgumentCaptor<Match> matchArgumentCaptor = ArgumentCaptor.forClass(Match.class);
        verify(matchRepository).save(matchArgumentCaptor.capture());
        verify(matchRepository).findByLocalTeamIdAndAwayTeamIdAndLeagueId(team1.getId(), team2.getId(), league.getId());
        assertThat(matchArgumentCaptor.getValue().getLocalTeam().getId()).isEqualTo(matchDto.getLocalTeamId());
    }

    @Test
    void shouldAddPlayerStatAndAddPoint() throws Exception {
        //given
        Long id1 = 1L;
        Long id2 = 2L;
        Long id3 = 3L;
        League league = new League();
        Team team1 = new Team();
        Team team2 = new Team();
        league.setId(id1);
        team1.setId(id2);
        team2.setId(id3);
        Match match = new Match(team1, team2, league);
        match.setId(1L);
        match.setHasEnded(false);
        PointDto pointDto = new PointDto(match.getId(), match.getLocalTeam().getId(), 1L, 2, 2L);
        when(matchRepository.findById(1L)).thenReturn(Optional.of(match));
        //when
        underTest.addPoint(pointDto);
        //then
        verify(playerStatService).addStatsToPlayer(1L, 2, 2L, match.getLeague());
        ArgumentCaptor<Match> argumentCaptor = ArgumentCaptor.forClass(Match.class);
        verify(matchRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getLocalScore()).isEqualTo(2);
    }

    @Test
    void shouldEndMatchAndAddTeamStat() throws Exception {
        //given
        Long id1 = 1L;
        Long id2 = 2L;
        Long id3 = 3L;
        League league = new League();
        Team team1 = new Team();
        Team team2 = new Team();
        league.setId(id1);
        team1.setId(id2);
        team2.setId(id3);
        Match match = new Match(team1, team2, league);
        match.setId(1L);
        match.setHasEnded(false);
        when(matchRepository.findById(1L)).thenReturn(Optional.of(match));
        when(teamStatService.getStatByLeagueIdAndTeamId(league.getId(), team1.getId())).thenThrow(new Exception());
        when(teamStatService.getStatByLeagueIdAndTeamId(league.getId(), team2.getId())).thenThrow(new Exception());
        //when
        underTest.endMatch(match.getId(), 26, 30);
        //then
        ArgumentCaptor<Match> argumentCaptor = ArgumentCaptor.forClass(Match.class);
        verify(matchRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().hasEnded()).isEqualTo(true);
        verify(teamStatService).saveStat(teamStatService.getStatByLeagueIdAndTeamId(league.getId(), team1.getId()));
        verify(teamStatService).saveStat(teamStatService.getStatByLeagueIdAndTeamId(league.getId(), team2.getId()));
    }
}