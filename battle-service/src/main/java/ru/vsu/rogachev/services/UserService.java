package ru.vsu.rogachev.services;

import ru.vsu.rogachev.connection.ConnectionManager;
import ru.vsu.rogachev.entities.Problem;
import ru.vsu.rogachev.entities.Submission;
import ru.vsu.rogachev.entities.User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private SubmissionService submissionService = new SubmissionService();
    private ProblemService problemService = new ProblemService();
    private ConnectionManager connectionManager = new ConnectionManager();

    public Set<Problem> getUserProblems(String handle) throws InterruptedException {
        Set<Problem> res = new HashSet<>();
        List<Submission> submissions = submissionService.getUserSubmissions(handle);
        for(Submission submission : submissions){
            if(submission.getVerdict() == Submission.Verdict.OK){
                res.add(submission.getProblem());
            }
        }

        return res;
    }

    public Set<String> getUserProblemSet(String handle) throws InterruptedException {
        Set<String> res = new HashSet<>();
        Set<Problem> problems = getUserProblems(handle);
        for(Problem problem : problems){
            res.add(problemService.getProblemUrl(problem));
        }

        return res;
    }

    public User getUser(String handle) throws InterruptedException {
        return connectionManager.getUser(handle);
    }

}
