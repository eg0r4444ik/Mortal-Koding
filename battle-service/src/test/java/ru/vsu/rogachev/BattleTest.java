package ru.vsu.rogachev;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.vsu.rogachev.codeforces.CodeforcesConnection;
import ru.vsu.rogachev.generator.TaskGenerator;
import ru.vsu.rogachev.repositories.GameSessionRepository;
import ru.vsu.rogachev.repositories.PlayerRepository;
import ru.vsu.rogachev.repositories.TaskRepository;
import ru.vsu.rogachev.services.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BattleTest {

    @Autowired
    private GameSessionRepository gameSessionRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private WaitingGameRepository waitingGameRepository;

    @Autowired
    private GameSessionService gameSessionService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private ProblemService problemService;

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private WaitingGameService waitingGameService;

    @Autowired
    private TaskGenerator taskGenerator;

    @Autowired
    private CodeforcesConnection codeforcesConnection;


    @Test
    void contextLoads() throws Exception {
        assertThat(gameSessionRepository).isNotNull();
        assertThat(playerRepository).isNotNull();
        assertThat(taskRepository).isNotNull();
        assertThat(waitingGameRepository).isNotNull();

        assertThat(gameSessionService).isNotNull();
        assertThat(playerService).isNotNull();
        assertThat(problemService).isNotNull();
        assertThat(submissionService).isNotNull();
        assertThat(taskService).isNotNull();
        assertThat(waitingGameService).isNotNull();

        assertThat(taskGenerator).isNotNull();
        assertThat(codeforcesConnection).isNotNull();
    }

}
