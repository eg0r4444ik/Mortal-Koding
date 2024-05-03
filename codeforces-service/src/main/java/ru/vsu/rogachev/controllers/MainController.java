package ru.vsu.rogachev.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.rogachev.connection.ConnectionManager;
import ru.vsu.rogachev.dto.Problem;
import ru.vsu.rogachev.dto.Submission;
import ru.vsu.rogachev.dto.User;

import java.util.List;

@RestController
public class MainController {

    @Autowired
    private ConnectionManager connectionManager;

    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/getPlayer/{handle}")
    public String getPlayer(@PathVariable(value = "handle") String handle) throws JsonProcessingException {
        User player = connectionManager.getPlayer(handle);
        String response = objectMapper.writeValueAsString(player);
        return response;
    }

    @GetMapping("/getPlayerSubmissions/{handle}")
    public String getPlayerSubmissions(@PathVariable(value = "handle") String handle) throws JsonProcessingException {
        List<Submission> submissions = connectionManager.getPlayerSubmissions(handle);
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
