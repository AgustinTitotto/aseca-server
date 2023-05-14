package com.example.asecaserver.controller;

import com.example.asecaserver.model.Player;
import com.example.asecaserver.model.PlayerStat;
import com.example.asecaserver.repository.PlayerRepository;
import com.example.asecaserver.repository.PlayerStatRepository;
import jakarta.transaction.Transactional;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PlayerStatControllerTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    @Autowired
    private PlayerStatRepository playerStatRepository;
    @Autowired
    private PlayerRepository playerRepository;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(String.valueOf(port)).concat("/playerStat");
    }


    @Test
    @DirtiesContext
    void shouldGetPlayerStatById() {
        //given
        Player player = new Player();
        player.setId(1L);
        player.setName("John S");
        playerRepository.save(player);
        PlayerStat playerStat = new PlayerStat();
        playerStat.setPlayer(player);
        playerStatRepository.save(playerStat);
        //when
        ResponseEntity<PlayerStat> response = restTemplate.getForEntity(baseUrl + "/getStat?playerId=" + "{id}", PlayerStat.class, 1L);
        PlayerStat responseStat = response.getBody();
        //then
        assert responseStat != null;
        assertThat(responseStat.getPlayer()).isNotNull();
        assertThat(responseStat.getPlayer().getId()).isEqualTo(playerStat.getPlayer().getId());
        assertThat(responseStat.getPlayer().getName()).isEqualTo(player.getName());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}