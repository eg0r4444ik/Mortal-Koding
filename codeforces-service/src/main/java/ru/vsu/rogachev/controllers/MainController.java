package ru.vsu.rogachev.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.rogachev.connection.ConnectionManager;
import ru.vsu.rogachev.models.Problem;
import ru.vsu.rogachev.models.Submission;
import ru.vsu.rogachev.models.User;

import java.util.List;

@RestController
public class MainController {

    @Autowired
    private ConnectionManager connectionManager;

    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/getUser/{handle}")
    public String getUser(@PathVariable(value = "handle") String handle) throws JsonProcessingException {
        User user = connectionManager.getUser(handle);
        String response = objectMapper.writeValueAsString(user);
        return response;
    }

    @GetMapping("/getUserSubmissions/{handle}")
    public String getUserSubmissions(@PathVariable(value = "handle") String handle) throws JsonProcessingException {
        List<Submission> submissions = connectionManager.getUserSubmissions(handle);
        String response = objectMapper.writeValueAsString(submissions);
        return response;
    }

    @GetMapping("/getProblemSet")
    public String getProblemSet() throws JsonProcessingException {
        List<Problem> problems = connectionManager.getProblemSet();
        String response = objectMapper.writeValueAsString(problems);
        return response;
    }

}
