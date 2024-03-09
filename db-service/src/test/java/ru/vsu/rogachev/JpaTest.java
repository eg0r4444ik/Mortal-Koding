package ru.vsu.rogachev;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

import ru.vsu.rogachev.repositories.GameSessionRepository;
import ru.vsu.rogachev.repositories.TaskRepository;
import ru.vsu.rogachev.services.GameSessionService;
import ru.vsu.rogachev.services.TaskService;

@SpringBootTest
public class JpaTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameSessionRepository gameSessionRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ConfirmRepository confirmRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private GameSessionService gameSessionService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ConfirmService confirmService;

    @Test
    void contextLoads() throws Exception {
        assertThat(userRepository).isNotNull();
        assertThat(gameSessionRepository).isNotNull();
        assertThat(taskRepository).isNotNull();
        assertThat(confirmRepository).isNotNull();

        assertThat(userService).isNotNull();
        assertThat(gameSessionService).isNotNull();
        assertThat(taskService).isNotNull();
        assertThat(confirmService).isNotNull();
    }

}
