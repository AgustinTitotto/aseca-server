package com.example.asecaserver.service.impl;

import com.example.asecaserver.model.Player;
import com.example.asecaserver.model.Team;
import com.example.asecaserver.repository.TeamRepository;
import com.example.asecaserver.service.PlayerService;
import com.example.asecaserver.service.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamServiceImplTest {

    @Mock
    private TeamRepository teamRepository;
    @Mock
    private PlayerService playerService;

    private TeamService underTest;

    @BeforeEach
    void setUp() {
        underTest = new TeamServiceImpl(teamRepository, playerService);
    }

    @Test
    void shouldFindById() throws Exception {
        //given
        Long id = 1L;
        Team team = new Team("Mavericks");
        when(teamRepository.findById(id)).thenReturn(Optional.of(team));
        //when
        underTest.findById(id);
        //then
        verify(teamRepository).findById(id);
    }

    @Test
    void shouldSaveSameTeamAsParameter() {
        //given
        String teamName1 = "teamName1";
        String teamName2 = "teamName2";
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            players.add(new Player());
        }
        when(playerService.savePlayers(12)).thenReturn(players);
        //when
        underTest.saveTeamsAndPlayer(Arrays.asList(teamName1, teamName2));
        //then
        ArgumentCaptor<Team> argumentCaptor = ArgumentCaptor.forClass(Team.class);
        verify(teamRepository, times(2)).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getAllValues().get(0).getTeamName()).isEqualTo(teamName1);
        assertThat(argumentCaptor.getAllValues().get(1).getTeamName()).isEqualTo(teamName2);
        assertThat(argumentCaptor.getAllValues().get(0).getPlayers().size()).isEqualTo(12);
        assertThat(argumentCaptor.getAllValues().get(1).getPlayers().size()).isEqualTo(12);
    }

    @Test
    void shouldGetTeamPlayers() throws Exception {
        //given
        Long id = 1L;
        Team team = new Team();
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            players.add(new Player());
        }
        team.setPlayers(players);
        when(teamRepository.findById(id)).thenReturn(Optional.of(team));
        //when
        List<Player> playerList = underTest.getPlayers(id);
        //then
        verify(teamRepository).findById(id);
        assertThat(playerList.size()).isEqualTo(players.size());
    }
}