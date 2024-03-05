package ru.vsu.rogachev.connection;

import ru.vsu.rogachev.converter.ObjectConverter;
import ru.vsu.rogachev.converter.impl.ObjectConverterImpl;
import ru.vsu.rogachev.entities.Problem;
import ru.vsu.rogachev.entities.Submission;
import ru.vsu.rogachev.entities.User;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class ConnectionManager {

    final RestTemplate restTemplate = new RestTemplate();
    ObjectConverter objectConverter = new ObjectConverterImpl();

    public User getUser(String handle) throws InterruptedException {
        String response = restTemplate.getForObject("https://codeforces.com/api/user.info?handles=" + handle, String.class);
        return objectConverter.getUser(response);
    }

    public List<Submission> getUserSubmissions(String handle) throws InterruptedException {
        String response = restTemplate.getForObject("https://codeforces.com/api/user.status?handle=" + handle, String.class);
        return objectConverter.getSubmissions(response);
    }

    public List<Problem> getProblemSet() throws InterruptedException {
        String response = restTemplate.getForObject("https://codeforces.com/api/problemset.problems", String.class);
        return objectConverter.getProblems(response);
    }

}
