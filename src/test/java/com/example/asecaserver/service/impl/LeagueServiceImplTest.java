package com.example.asecaserver.service.impl;

import com.example.asecaserver.model.League;
import com.example.asecaserver.model.Team;
import com.example.asecaserver.model.dtos.CreateLeagueDto;
import com.example.asecaserver.repository.LeagueRepository;
import com.example.asecaserver.service.ExternalApiService;
import com.example.asecaserver.service.LeagueService;
import com.example.asecaserver.service.TeamService;
import com.example.asecaserver.service.TeamStatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LeagueServiceImplTest {

    @Mock
    private LeagueRepository leagueRepository;
    @Mock
    private TeamService teamService;
    @Mock
    private ExternalApiService externalApiService;
    @Mock
    private TeamStatService teamStatService;

    private LeagueService underTest;

    @BeforeEach
    void setUp() {
        underTest = new LeagueServiceImpl(leagueRepository, teamService, externalApiService, teamStatService);
    }

    @Test
    void shouldFindAllLeagues() {
        //given
        List<League> leagues = Arrays.asList(new League("NBA"), new League("NBA2"));
        when(leagueRepository.findAll()).thenReturn(leagues);
        //when
        List<League> leagues1 = underTest.findAll();
        //then
        verify(leagueRepository).findAll();
        assertThat(leagues1).isEqualTo(leagues);
    }

    @Test
    void shouldFindById() throws Exception {
        //given
        Long id = 1L;
        League league = new League("NBA");
        when(leagueRepository.findById(id)).thenReturn(Optional.of(league));
        //when
        underTest.findById(id);
        //then
        verify(leagueRepository).findById(id);
    }

    @Test
    void shouldSaveLeague() throws Exception {
        //given
        League league = new League("NBA");
        league.setId(1L);
        Team team1 = new Team("team1");
        Team team2 = new Team("team2");
        team1.setId(1L);
        team2.setId(2L);
        List<Team> teams = Arrays.asList(team1, team2);
        CreateLeagueDto createLeagueDto = new CreateLeagueDto(league.getLeagueName(), Arrays.asList(team1.getTeamName(), team2.getTeamName()), new Date(2026, Calendar.OCTOBER, 10), new Date(2026, Calendar.NOVEMBER, 10));
        when(teamService.saveTeamsAndPlayer(Arrays.asList(team1.getTeamName(), team2.getTeamName()))).thenReturn(teams);
        when(leagueRepository.save(ArgumentMatchers.any(League.class))).thenReturn(league);
        //when
        underTest.addLeague(createLeagueDto.getLeagueName(), createLeagueDto.getTeams(), createLeagueDto.getStartDate(), createLeagueDto.getFinishDate());
        //then
        ArgumentCaptor<League> argumentCaptor = ArgumentCaptor.forClass(League.class);
        verify(leagueRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getLeagueName()).isEqualTo(league.getLeagueName());
        assertThat(argumentCaptor.getValue().getTeams().size()).isEqualTo(2);
    }
}