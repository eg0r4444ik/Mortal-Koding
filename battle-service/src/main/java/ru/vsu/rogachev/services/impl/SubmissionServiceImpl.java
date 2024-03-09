package ru.vsu.rogachev.services.impl;

import ru.vsu.rogachev.models.Submission;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.services.SubmissionService;

import java.util.List;

@Service
public class SubmissionServiceImpl implements SubmissionService {


    public List<Submission> getUserSubmissions(String handle) throws InterruptedException {
        return connectionManager.getUserSubmissions(handle);
    }

}
