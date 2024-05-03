package ru.vsu.rogachev.converter;

import ru.vsu.rogachev.dto.Problem;
import ru.vsu.rogachev.dto.Submission;
import ru.vsu.rogachev.dto.User;

import java.util.List;

public interface ObjectConverter {

    public User getPlayer(String response);
    public Problem getProblem(String response);
    public Submission getSubmission(String response);
    public List<Problem> getProblems(String response);
    public List<Submission> getSubmissions(String response);

}
