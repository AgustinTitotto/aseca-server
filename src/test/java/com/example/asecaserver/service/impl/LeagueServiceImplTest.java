package com.example.asecaserver.service.impl;

import com.example.asecaserver.model.League;
import com.example.asecaserver.model.Team;
import com.example.asecaserver.model.dtos.CreateLeagueDto;
import com.example.asecaserver.repository.LeagueRepository;
import com.example.asecaserver.service.PlayerService;
import com.example.asecaserver.service.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    private PlayerService playerService;

    private LeagueServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new LeagueServiceImpl(leagueRepository, teamService, playerService);
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
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(leagueRepository).findById(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(id);
    }

    @Test
    void shouldSaveLeague() {
        //given
        League league = new League("NBA");
        String leagueName = league.getLeagueName();
        String team1 = "team1";
        String team2 = "team2";
        List<Team> teams = Arrays.asList(new Team(team1), new Team(team2));
        CreateLeagueDto createLeagueDto = new CreateLeagueDto(leagueName, Arrays.asList(team1, team2));
        //when
        underTest.addLeague(createLeagueDto.getLeague(), createLeagueDto.getTeams());
        //then
        ArgumentCaptor<League> argumentCaptor = ArgumentCaptor.forClass(League.class);
        verify(leagueRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getLeagueName()).isEqualTo(leagueName);
        assertThat(argumentCaptor.getValue().getTeams().size()).isEqualTo(teams.size());
        assertThat(argumentCaptor.getValue().getTeams().get(0).getPlayers().size()).isEqualTo(12);
    }
}