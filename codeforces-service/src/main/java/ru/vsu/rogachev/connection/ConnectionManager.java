package ru.vsu.rogachev.connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.vsu.rogachev.converter.ObjectConverter;
import ru.vsu.rogachev.converter.impl.ObjectConverterImpl;
import ru.vsu.rogachev.models.Problem;
import ru.vsu.rogachev.models.Submission;
import ru.vsu.rogachev.models.User;

import java.util.List;

@Service
public class ConnectionManager {

    final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private ObjectConverter objectConverter;

    public User getUser(String handle){
        String response = restTemplate.getForObject("https://codeforces.com/api/user.info?handles=" + handle, String.class);
        return objectConverter.getUser(response);
    }

    public List<Submission> getUserSubmissions(String handle){
        String response = restTemplate.getForObject("https://codeforces.com/api/user.status?handle=" + handle, String.class);
        return objectConverter.getSubmissions(response);
    }

    public List<Problem> getProblemSet(){
        String response = restTemplate.getForObject("https://codeforces.com/api/problemset.problems", String.class);
        return objectConverter.getProblems(response);
    }

}
