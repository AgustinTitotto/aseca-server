package com.example.asecaserver.service.impl;

import com.example.asecaserver.model.TeamStat;
import com.example.asecaserver.repository.TeamStatRepository;
import com.example.asecaserver.service.TeamStatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamStatServiceImplTest {

    @Mock
    private TeamStatRepository teamStatRepository;

    private TeamStatService underTest;

    @BeforeEach
    void setUp() {
        underTest = new TeamStatServiceImpl(teamStatRepository);
    }

    @Test
    void getStatByLeagueIdAndTeamId() throws Exception {
        //given
        Long leagueId = 1L;
        Long teamId = 1L;
        TeamStat teamStat = new TeamStat();
        when(teamStatRepository.findByLeagueIdAndTeamId(leagueId, teamId)).thenReturn(Optional.of(teamStat));
        //when
        underTest.getStatByLeagueIdAndTeamId(leagueId, teamId);
        //then
        verify(teamStatRepository).findByLeagueIdAndTeamId(leagueId, teamId);
    }

    @Test
    void saveStat() {
        //given
        TeamStat teamStat = new TeamStat();
        //when
        underTest.saveStat(teamStat);
        //then
        verify(teamStatRepository).save(teamStat);
    }

    @Test
    void getLeagueTable() {
        //given
        Long leagueId = 1L;
        //when
        underTest.getLeagueTable(leagueId);
        //then
        verify(teamStatRepository).findAllByLeagueId(leagueId);
    }
}