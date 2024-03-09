package ru.vsu.rogachev.services.impl;

import ru.vsu.rogachev.models.Problem;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.services.ProblemService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProblemServiceImpl implements ProblemService {

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
