package ru.vsu.rogachev.services.impl;

import ru.vsu.rogachev.models.Problem;
import ru.vsu.rogachev.models.Submission;
import ru.vsu.rogachev.models.Player;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PlayerServiceImpl {

    private SubmissionServiceImpl submissionService = new SubmissionServiceImpl();
    private ProblemServiceImpl problemService = new ProblemServiceImpl();

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

    public Player getUser(String handle) throws InterruptedException {
        return connectionManager.getUser(handle);
    }

}
