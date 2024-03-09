package ru.vsu.rogachev.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.vsu.rogachev.dto.SubmissionDTO;

import java.util.List;

public interface SubmissionService {

    List<SubmissionDTO> getPlayerSubmissions(String handle) throws InterruptedException, JsonProcessingException;

}
