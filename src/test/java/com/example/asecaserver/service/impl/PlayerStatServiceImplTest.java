package com.example.asecaserver.service.impl;

import com.example.asecaserver.model.League;
import com.example.asecaserver.model.Player;
import com.example.asecaserver.model.PlayerStat;
import com.example.asecaserver.repository.PlayerStatRepository;
import com.example.asecaserver.service.PlayerService;
import com.example.asecaserver.service.PlayerStatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PlayerStatServiceImplTest {

    @Mock
    private PlayerStatRepository playerStatRepository;
    @Mock
    private PlayerService playerService;

    private PlayerStatService underTest;

    @BeforeEach
    void setUp() {
        underTest = new PlayerStatServiceImpl(playerStatRepository, playerService);
    }

    @Test
    void getPlayerStatById() throws Exception {
        //given
        Long id = 1L;
        PlayerStat playerStat = new PlayerStat();
        when(playerStatRepository.findByPlayerId(id)).thenReturn(Optional.of(playerStat));
        //when
        underTest.getPlayerStatById(id);
        //then
        verify(playerStatRepository).findByPlayerId(id);

    }

    @Test
    void addStatsToPlayer() throws Exception {
        //given
        Player scoringPlayer = new Player();
        Long player1Id = 1L;
        scoringPlayer.setId(player1Id);
        Player assistingPlayer = new Player();
        Long player2Id = 2L;
        assistingPlayer.setId(player2Id);
        League league = new League();
        when(playerStatRepository.findByPlayerId(any())).thenReturn(Optional.empty());
        when(playerService.findById(player1Id)).thenReturn(scoringPlayer);
        when(playerService.findById(player2Id)).thenReturn(assistingPlayer);
        //when
        underTest.addStatsToPlayer(player1Id, 2, player2Id, league);
        //then
        ArgumentCaptor<PlayerStat> argumentCaptor = ArgumentCaptor.forClass(PlayerStat.class);
        verify(playerStatRepository, times(2)).save(argumentCaptor.capture());
        PlayerStat scoringPlayerStat = argumentCaptor.getAllValues().get(0);
        PlayerStat assistingPlayerStat = argumentCaptor.getAllValues().get(1);
        assertThat(scoringPlayerStat.getPoints2()).isEqualTo(1);
        assertThat(scoringPlayerStat.getPointsScored()).isEqualTo(2);
        assertThat(assistingPlayerStat.getAssists()).isEqualTo(1);
    }
}