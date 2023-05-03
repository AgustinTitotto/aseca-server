package com.example.asecaserver.service.impl;

import com.example.asecaserver.model.Team;
import com.example.asecaserver.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamServiceImplTest {

    @Mock
    private TeamRepository teamRepository;


    private TeamServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new TeamServiceImpl(teamRepository);
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
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(teamRepository).findById(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(id);
    }

    @Test
    void shouldSaveSameTeamAsParameter() {
        //given
        Team team = new Team();
        //when
        underTest.insert(team);
        //then
        ArgumentCaptor<Team> argumentCaptor = ArgumentCaptor.forClass(Team.class);
        verify(teamRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(team);
    }
}