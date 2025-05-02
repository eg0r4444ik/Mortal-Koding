package ru.vsu.rogachev.utils;

import lombok.experimental.UtilityClass;
import ru.vsu.rogachev.client.codeforces.dto.Problems;

import javax.validation.constraints.NotNull;

@UtilityClass
public class TaskUtils {

    private static final String PROBLEM_URL_PREFIX = "https://codeforces.com/problemset/problem/";

    @NotNull
    public String getProblemUrl(@NotNull Problems.Problem problem){
        return PROBLEM_URL_PREFIX + problem.getContestId() + "/" + problem.getIndex();
    }

}
