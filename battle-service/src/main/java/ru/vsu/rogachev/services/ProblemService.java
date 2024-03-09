package ru.vsu.rogachev.services;

import ru.vsu.rogachev.models.Problem;

import java.util.List;

public interface ProblemService {

    String getProblemUrl(Problem problem);

    List<Problem> getProblems() throws InterruptedException;

    List<String> getProblemSet(List<Problem> problems);
}
