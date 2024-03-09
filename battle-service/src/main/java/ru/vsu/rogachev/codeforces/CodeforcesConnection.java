package ru.vsu.rogachev.codeforces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.vsu.rogachev.dto.PlayerDTO;
import ru.vsu.rogachev.entities.Player;
import ru.vsu.rogachev.dto.ProblemDTO;
import ru.vsu.rogachev.dto.SubmissionDTO;

import java.util.List;

@Service
public class CodeforcesConnection {

    @Value("${codeforces.host}")
    private String host;

    @Value("${codeforces.port}")
    private String port;

    private final String path;

    final RestTemplate restTemplate = new RestTemplate();

    private ObjectMapper objectMapper = new ObjectMapper();

    public PlayerDTO getPlayer(String handle) throws JsonProcessingException {
        String response = restTemplate.getForObject(path + "/getPlayer/" + handle, String.class);
        return objectMapper.readValue(response, PlayerDTO.class);
    }

    public List<SubmissionDTO> getPlayerSubmissions(String handle) throws JsonProcessingException {
        String response = restTemplate.getForObject(path + "/getPlayerSubmissions/" + handle, String.class);
        List<SubmissionDTO> submissions = objectMapper.readValue(response, new TypeReference<List<SubmissionDTO>>(){});
        return submissions;
    }

    public List<ProblemDTO> getProblemSet() throws JsonProcessingException {
        String response = restTemplate.getForObject(path + "/getProblemSet", String.class);
        List<ProblemDTO> problems = objectMapper.readValue(response, new TypeReference<List<ProblemDTO>>(){});
        return problems;
    }

    @Autowired
    public CodeforcesConnection(@Value("${codeforces.host}") final String host, @Value("${codeforces.port}") final String port) {
        this.host = host;
        this.port = port;
        this.path = "http://" + host + ":" + port;
    }
}
