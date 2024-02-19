package connection;

import converter.ObjectConverter;
import converter.impl.ObjectConverterImpl;
import entities.Problem;
import entities.Submission;
import entities.User;
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
