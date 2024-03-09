package ru.vsu.rogachev.services;

import ru.vsu.rogachev.models.Submission;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmissionService {


    public List<Submission> getUserSubmissions(String handle) throws InterruptedException {
        return connectionManager.getUserSubmissions(handle);
    }

}
