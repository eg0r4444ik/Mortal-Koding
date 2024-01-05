package services;

import connection.ConnectionManager;
import entities.Problem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProblemService {

    private ConnectionManager connectionManager = new ConnectionManager();

    public String getProblemUrl(Problem problem){
        return "https://codeforces.com/problemset/problem/" + problem.getContestId() + "/" + problem.getIndex();
    }

    public List<Problem> getProblems() throws InterruptedException {
        return connectionManager.getProblemSet();
    }

    public List<String> getProblemSet(List<Problem> problems){
        List<String> result = new ArrayList<>();
        for(Problem problem : problems){
            result.add(getProblemUrl(problem));
        }

        return result;
    }

}
