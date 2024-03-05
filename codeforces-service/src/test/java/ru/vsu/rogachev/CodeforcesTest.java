package ru.vsu.rogachev;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

import ru.vsu.rogachev.connection.ConnectionManager;
import ru.vsu.rogachev.converter.ObjectConverter;

@SpringBootTest
public class CodeforcesTest {

    @Autowired
    private ObjectConverter objectConverter;

    @Autowired
    private ConnectionManager connectionManager;

    @Test
    void contextLoads() throws Exception {
        assertThat(objectConverter).isNotNull();
        assertThat(connectionManager).isNotNull();
    }


}
