package ru.vsu.rogachev.connection;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.vsu.rogachev.models.Problem;
import ru.vsu.rogachev.models.Submission;
import ru.vsu.rogachev.models.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class ConnectionManagerTest {

    @Autowired
    private ConnectionManager connectionManager;

    @Test
    void getUser() throws Exception {
        User user1 = connectionManager.getUser("egor4444ik");
        assertThat(user1).isNotNull();
        assertThat(user1.getEmail()).isNotNull();
        assertThat(user1.getEmail()).isEqualTo("egorchik.rog@yandex.ru");
        assertThat(user1.getHandle()).isEqualTo("egor4444ik");

        User user2 = connectionManager.getUser("iamdimonis");
        assertThat(user2).isNotNull();
        assertThat(user2.getEmail()).isNull();
        assertThat(user2.getHandle()).isEqualTo("iamdimonis");
    }

    @Test
    void getUserSubmissions() throws Exception {
        List<Submission> submissions = connectionManager.getUserSubmissions("egor4444ik");
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
