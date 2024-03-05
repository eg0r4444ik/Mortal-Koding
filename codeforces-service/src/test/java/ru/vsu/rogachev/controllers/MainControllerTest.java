package ru.vsu.rogachev.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.vsu.rogachev.connection.ConnectionManager;
import ru.vsu.rogachev.models.Problem;
import ru.vsu.rogachev.models.Submission;
import ru.vsu.rogachev.models.User;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MainController.class)
public class MainControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    ConnectionManager connectionManager;

    @Test
    void getUser() throws Exception {
        Mockito.when(this.connectionManager.getUser("egor4444ik")).thenReturn(getUserWithEmail());

        mvc.perform(get("/getUser/egor4444ik"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating").value(1600))
                .andExpect(jsonPath("$.handle").value("egor4444ik"))
                .andExpect(jsonPath("$.email").value("egorchik.rog@yandex.ru"));

        Mockito.when(this.connectionManager.getUser("iamdimonis")).thenReturn(getUserWithoutEmail());

        mvc.perform(get("/getUser/iamdimonis"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating").value(1500))
                .andExpect(jsonPath("$.handle").value("iamdimonis"))
                .andExpect(jsonPath("$.email").isEmpty());
    }

    @Test
    void getUserSubmissions() throws Exception {
        Mockito.when(this.connectionManager.getUserSubmissions("egor4444ik")).thenReturn(getSubmissions());

        mvc.perform(get("/getUserSubmissions/egor4444ik"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
//                .andExpect(jsonPath("$.get(0).getCreationTimeSeconds()").value(2000))
//                .andExpect(jsonPath("$.get(0).getProblem().getcontestId()").value(1))
//                .andExpect(jsonPath("$.get(0).getProblem().getIndex()").value("A"))
//                .andExpect(jsonPath("$.get(0).getProblem().getName()").value("Game"))
//                .andExpect(jsonPath("$.get(0).problem.type").value(Problem.Type.PROGRAMMING))
//                .andExpect(jsonPath("$.get(0).getProblem().getRating()").value(1600))
//                .andExpect(jsonPath("$.get(0).getProblem().getTags().length()").value(0))
//                .andExpect(jsonPath("$.get(0).getProgrammingLanguage()").value("java"))
//                .andExpect(jsonPath("$.get(0).getVerdict()").value(Submission.Verdict.OK));
    }

    @Test
    void getProblemSet() throws Exception {
        Mockito.when(this.connectionManager.getProblemSet()).thenReturn(getProblems());

        mvc.perform(get("/getProblemSet"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    private User getUserWithEmail(){
        return new User(1600, "egor4444ik", "egorchik.rog@yandex.ru");
    }

    private User getUserWithoutEmail(){
        return new User(1500, "iamdimonis", null);
    }

    private List<Submission> getSubmissions(){
        List<Submission> result = new ArrayList<>(){{
            add(new Submission(2000, new Problem(1, "A", "Game", Problem.Type.PROGRAMMING,
                    1600, new ArrayList<>()), "java", Submission.Verdict.OK));
            add(new Submission(5000, new Problem(1, "B", "Home", Problem.Type.PROGRAMMING,
                    1800, new ArrayList<>()), "java", Submission.Verdict.WRONG_ANSWER));
        }};

        return result;
    }

    private List<Problem> getProblems(){
        List<Problem> result = new ArrayList<>(){{
            add(new Problem(1, "A", "Game", Problem.Type.PROGRAMMING,
                    1600, new ArrayList<>()));
            add(new Problem(1, "B", "Home", Problem.Type.PROGRAMMING,
                    1800, new ArrayList<>()));
        }};

        return result;
    }
}
