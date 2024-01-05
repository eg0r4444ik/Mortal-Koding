package services;

import connection.ConnectionManager;
import entities.Submission;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmissionService {

    private ConnectionManager connectionManager = new ConnectionManager();

    public List<Submission> getUserSubmissions(String handle) throws InterruptedException {
        return connectionManager.getUserSubmissions(handle);
    }

}
