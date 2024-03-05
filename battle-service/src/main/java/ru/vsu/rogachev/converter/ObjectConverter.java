package ru.vsu.rogachev.converter;

import ru.vsu.rogachev.entities.Problem;
import ru.vsu.rogachev.entities.Submission;
import ru.vsu.rogachev.entities.User;

import java.util.List;

public interface ObjectConverter {

    public User getUser(String response);
    public Problem getProblem(String response);
    public Submission getSubmission(String response);
    public List<Problem> getProblems(String response);
    public List<Submission> getSubmissions(String response);

}
