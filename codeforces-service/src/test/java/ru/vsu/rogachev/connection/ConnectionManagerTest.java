package ru.vsu.rogachev.connection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.vsu.rogachev.dto.Problem;
import ru.vsu.rogachev.dto.Submission;
import ru.vsu.rogachev.dto.Player;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class ConnectionManagerTest {

    @Autowired
    private ConnectionManager connectionManager;

    @Test
    void getPlayer() throws Exception {
        Player player1 = connectionManager.getPlayer("egor4444ik");
        assertThat(player1).isNotNull();
        assertThat(player1.getEmail()).isNotNull();
        assertThat(player1.getEmail()).isEqualTo("egorchik.rog@yandex.ru");
        assertThat(player1.getHandle()).isEqualTo("egor4444ik");

        Player player2 = connectionManager.getPlayer("Just4Fun_");
        assertThat(player2).isNotNull();
        assertThat(player2.getEmail()).isNull();
        assertThat(player2.getHandle()).isEqualTo("Just4Fun_");
    }

    @Test
    void getPlayerSubmissions() throws Exception {
        List<Submission> submissions = connectionManager.getPlayerSubmissions("egor4444ik");
        assertThat(submissions).isNotNull();
        assertThat(submissions).isNotEmpty();
    }

    @Test
    void getProblemSet() throws Exception {
        List<Problem> problems = connectionManager.getProblemSet();
        assertThat(problems).isNotNull();
        assertThat(problems).isNotEmpty();
    }
}
