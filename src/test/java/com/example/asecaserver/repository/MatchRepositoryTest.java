package com.example.asecaserver.repository;

import com.example.asecaserver.model.League;
import com.example.asecaserver.model.Match;
import com.example.asecaserver.model.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class MatchRepositoryTest {

    @Autowired
    private MatchRepository underTest;
    @Autowired
    private LeagueRepository leagueRepository;
    @Autowired
    private TeamRepository teamRepository;

    @Test
    void itShouldFindByLocalTeamIdAndAwayTeamIdAndLeagueId() {
        //given
        League league = new League("NBA");
        Team team1 = new Team("Mavericks");
        Team team2 = new Team("Lakers");
        leagueRepository.save(league);
        teamRepository.save(team1);
        teamRepository.save(team2);
        Match match = new Match(team1, team2, league);
        underTest.save(match);
        //when
        Optional<Match> exists = underTest.findByLocalTeamIdAndAwayTeamIdAndLeagueId(team1.getId(), team2.getId(), league.getId());
        //then
        assertThat(exists).isPresent();
    }
}