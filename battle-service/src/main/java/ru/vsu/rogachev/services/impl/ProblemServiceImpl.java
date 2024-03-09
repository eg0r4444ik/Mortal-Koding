package ru.vsu.rogachev.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import ru.vsu.rogachev.codeforces.CodeforcesConnection;
import ru.vsu.rogachev.dto.ProblemDTO;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.services.ProblemService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProblemServiceImpl implements ProblemService {

    @Autowired
    private CodeforcesConnection codeforcesConnection;

    public String getProblemUrl(ProblemDTO problem){
        return "https://codeforces.com/problemset/problem/" + problem.getContestId() + "/" + problem.getIndex();
    }

    public List<ProblemDTO> getProblems() throws InterruptedException, JsonProcessingException {
        return codeforcesConnection.getProblemSet();
    }

    public List<String> getProblemSet(List<ProblemDTO> problems){
        List<String> result = new ArrayList<>();
        for(ProblemDTO problem : problems){
            result.add(getProblemUrl(problem));
        }

        return result;
    }

}
