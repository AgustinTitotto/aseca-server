package com.example.asecaserver.service.impl;

import com.example.asecaserver.model.League;
import com.example.asecaserver.model.Team;
import com.example.asecaserver.model.dtos.CreateLeagueDto;
import com.example.asecaserver.repository.LeagueRepository;
import com.example.asecaserver.repository.PlayerRepository;
import com.example.asecaserver.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LeagueServiceImplTest {

    @Mock
    private LeagueRepository leagueRepository;
    @Mock
    private TeamRepository teamRepository;
    @Mock
    private PlayerRepository playerRepository;

    private LeagueServiceImpl underTest;

    @BeforeEach
    void setUp() {
        TeamServiceImpl teamService = new TeamServiceImpl(teamRepository);
        PlayerServiceImpl playerService = new PlayerServiceImpl(playerRepository);
        underTest = new LeagueServiceImpl(leagueRepository, teamService, playerService);
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
    void shouldSaveSameLeagueAsParameter() {
        //given
        String leagueName = "NBA";
        String team1 = "team1";
        String team2 = "team2";
        CreateLeagueDto createLeagueDto = new CreateLeagueDto(leagueName, Arrays.asList(team1, team2));
        //when
        underTest.addLeague(createLeagueDto.getLeague(), createLeagueDto.getTeams());
        //then
        ArgumentCaptor<League> argumentCaptor = ArgumentCaptor.forClass(League.class);
        verify(leagueRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(leagueName);
    }
}