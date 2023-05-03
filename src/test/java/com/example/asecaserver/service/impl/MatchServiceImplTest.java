package com.example.asecaserver.service.impl;

import com.example.asecaserver.model.*;
import com.example.asecaserver.model.dtos.MatchDto;
import com.example.asecaserver.repository.*;
import com.example.asecaserver.service.TeamStatService;
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
class MatchServiceImplTest {

    @Mock
    private MatchRepository matchRepository;
    @Mock
    private LeagueRepository leagueRepository;
    @Mock
    private TeamRepository teamRepository;
    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private TeamStatRepository teamStatRepository;
    @Mock
    private PlayerStatRepository playerStatRepository;

    private MatchServiceImpl underTest;

    @BeforeEach
    void setUp() {
        TeamServiceImpl teamService = new TeamServiceImpl(teamRepository);
        PlayerServiceImpl playerService = new PlayerServiceImpl(playerRepository);
        LeagueServiceImpl leagueService = new LeagueServiceImpl(leagueRepository, teamService, playerService);
        TeamStatServiceImpl teamStatService = new TeamStatServiceImpl(teamStatRepository);
        PlayerStatServiceImpl playerStatService = new PlayerStatServiceImpl(playerStatRepository, playerService);
        underTest = new MatchServiceImpl(matchRepository, leagueService, teamService, teamStatService, playerStatService);
    }

    @Test
    void shouldCheckForTeamsAndLeagueAndSaveMatch() throws Exception {
        //given
        Long id2 = 2L;
        Long id3 = 3L;
        League league = new League();
        Team team1 = new Team();
        Team team2 = new Team();
        team1.setId(id2);
        team2.setId(id3);
        MatchDto matchDto = new MatchDto(team1.getId(), team2.getId(), league.getId());
        when(matchRepository.findByLocalTeamIdAndAwayTeamIdAndLeagueId(team1.getId(), team2.getId(), league.getId())).thenReturn(Optional.empty());
        when(leagueRepository.findById(league.getId())).thenReturn(Optional.of(league));
        when(teamRepository.findById(team1.getId())).thenReturn(Optional.of(team1));
        when(teamRepository.findById(team2.getId())).thenReturn(Optional.of(team2));
        //when
        underTest.createMatch(matchDto);
        //then
        ArgumentCaptor<Match> matchArgumentCaptor = ArgumentCaptor.forClass(Match.class);
        verify(matchRepository).save(matchArgumentCaptor.capture());
        assertThat(matchArgumentCaptor.getValue().getLocalTeam().getId()).isEqualTo(matchDto.getLocalTeamId());
    }
}