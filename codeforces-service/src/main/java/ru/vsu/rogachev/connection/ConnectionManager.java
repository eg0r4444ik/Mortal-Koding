package ru.vsu.rogachev.connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.vsu.rogachev.converter.ObjectConverter;
import ru.vsu.rogachev.dto.Problem;
import ru.vsu.rogachev.dto.Submission;
import ru.vsu.rogachev.dto.User;

import java.util.List;

@Service
public class ConnectionManager {

    final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private ObjectConverter objectConverter;

    public User getPlayer(String handle){
        String response = restTemplate.getForObject("https://codeforces.com/api/user.info?handles=" + handle, String.class);
        return objectConverter.getPlayer(response);
    }

    public List<Submission> getPlayerSubmissions(String handle){
        String response = restTemplate.getForObject("https://codeforces.com/api/user.status?handle=" + handle, String.class);
        return objectConverter.getSubmissions(response);
    }

    public List<Problem> getProblemSet(){
        String response = restTemplate.getForObject("https://codeforces.com/api/problemset.problems", String.class);
        return objectConverter.getProblems(response);
    }

}
