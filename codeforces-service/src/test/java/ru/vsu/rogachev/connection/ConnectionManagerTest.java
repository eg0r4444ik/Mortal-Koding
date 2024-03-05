package ru.vsu.rogachev.connection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class ConnectionManagerTest {

    @Autowired
    private ConnectionManager connectionManager;

    @Test
    void getUser() throws Exception {
        assertThat(connectionManager.getUser("egor4444ik")).isNotNull();
        Thread.sleep(1000);
        assertThat(connectionManager.getUser("egor4444ik").getEmail()).isNotNull();
        Thread.sleep(1000);
        assertThat(connectionManager.getUser("egor4444ik").getEmail()).isEqualTo("egorchik.rog@yandex.ru");
        Thread.sleep(1000);
        assertThat(connectionManager.getUser("egor4444ik").getHandle()).isEqualTo("egor4444ik");
        Thread.sleep(1000);

        assertThat(connectionManager.getUser("iamdimonis")).isNotNull();
        Thread.sleep(1000);
        assertThat(connectionManager.getUser("iamdimonis").getEmail()).isNull();
        Thread.sleep(1000);
        assertThat(connectionManager.getUser("iamdimonis").getHandle()).isEqualTo("iamdimonis");
        Thread.sleep(1000);
    }

    @Test
    void getUserSubmissions() throws Exception {

    }

    @Test
    void getProblemSet() throws Exception {

    }
}
