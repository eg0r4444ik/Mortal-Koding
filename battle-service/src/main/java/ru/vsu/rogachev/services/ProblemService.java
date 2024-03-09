package ru.vsu.rogachev.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.vsu.rogachev.dto.ProblemDTO;

import java.util.List;

public interface ProblemService {

    String getProblemUrl(ProblemDTO problem);

    List<ProblemDTO> getProblems() throws InterruptedException, JsonProcessingException;

    List<String> getProblemSet(List<ProblemDTO> problems);
}
