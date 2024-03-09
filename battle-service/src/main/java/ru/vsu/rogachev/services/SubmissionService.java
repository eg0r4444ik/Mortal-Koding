package ru.vsu.rogachev.services;

import ru.vsu.rogachev.models.Submission;

import java.util.List;

public interface SubmissionService {

    List<Submission> getUserSubmissions(String handle) throws InterruptedException;

}
