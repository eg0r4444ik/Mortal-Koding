package ru.vsu.rogachev.connections;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.vsu.rogachev.dto.UserDTO;

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

    public UserDTO getUser(String handle) throws JsonProcessingException {
        String response = restTemplate.getForObject(path + "/getPlayer/" + handle, String.class);
        UserDTO userDTO = objectMapper.readValue(response, UserDTO.class);
        return userDTO;
    }

    @Autowired
    public CodeforcesConnection(@Value("${codeforces.host}") final String host, @Value("${codeforces.port}") final String port) {
        this.host = host;
        this.port = port;
        this.path = "http://" + host + ":" + port;
    }
}
