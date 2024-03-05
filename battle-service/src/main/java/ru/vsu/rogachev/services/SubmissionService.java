package ru.vsu.rogachev.services;

import ru.vsu.rogachev.connection.ConnectionManager;
import ru.vsu.rogachev.entities.Submission;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmissionService {

    private ConnectionManager connectionManager = new ConnectionManager();

    public List<Submission> getUserSubmissions(String handle) throws InterruptedException {
        return connectionManager.getUserSubmissions(handle);
    }

}
