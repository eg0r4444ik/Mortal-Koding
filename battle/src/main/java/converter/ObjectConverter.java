package converter;

import entities.Problem;
import entities.Submission;
import entities.User;

import java.util.List;

public interface ObjectConverter {

    public User getUser(String response);
    public Problem getProblem(String response);
    public Submission getSubmission(String response);
    public List<Problem> getProblems(String response);
    public List<Submission> getSubmissions(String response);

}
