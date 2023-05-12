package com.example.asecaserver.service.impl;

import com.example.asecaserver.model.Player;
import com.example.asecaserver.repository.PlayerRepository;
import com.example.asecaserver.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerServiceImplTest {

    @Mock
    private PlayerRepository playerRepository;

    private PlayerService underTest;

    @BeforeEach
    void setUp() {
        underTest = new PlayerServiceImpl(playerRepository);
    }

    @Test
    void shouldFindById() throws Exception {
        //given
        Long id = 1L;
        Player player = new Player("John F");
        when(playerRepository.findById(id)).thenReturn(Optional.of(player));
        //when
        underTest.findById(id);
        //then
        verify(playerRepository).findById(id);
    }

    @Test
    void savePlayers() {
        //given
        //when
        underTest.savePlayers(12);
        //then
        ArgumentCaptor<Player> argumentCaptor = ArgumentCaptor.forClass(Player.class);
        verify(playerRepository, times(12)).save(argumentCaptor.capture());
    }
}