package ru.vsu.rogachev.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import ru.vsu.rogachev.codeforces.CodeforcesConnection;
import ru.vsu.rogachev.dto.SubmissionDTO;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.services.SubmissionService;

import java.util.List;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    @Autowired
    private CodeforcesConnection codeforcesConnection;

    @Override
    public List<SubmissionDTO> getPlayerSubmissions(String handle) throws InterruptedException, JsonProcessingException {
        return codeforcesConnection.getPlayerSubmissions(handle);
    }

}
