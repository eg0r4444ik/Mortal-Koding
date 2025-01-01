package ru.vsu.rogachev.converter;

import ru.vsu.rogachev.dto.Problem;
import ru.vsu.rogachev.dto.Submission;
import ru.vsu.rogachev.dto.User;

import java.util.List;

public interface ObjectConverter {

    User getPlayer(String response);
    Problem getProblem(String response);
    Submission getSubmission(String response);
    List<Problem> getProblems(String response);
    List<Submission> getSubmissions(String response);

}
