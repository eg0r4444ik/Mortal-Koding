package ru.vsu.rogachev.converter;

import ru.vsu.rogachev.models.Problem;
import ru.vsu.rogachev.models.Submission;
import ru.vsu.rogachev.models.Player;

import java.util.List;

public interface ObjectConverter {

    public Player getPlayer(String response);
    public Problem getProblem(String response);
    public Submission getSubmission(String response);
    public List<Problem> getProblems(String response);
    public List<Submission> getSubmissions(String response);

}
