package ru.vsu.rogachev.connections;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.vsu.rogachev.dto.UserDTO;

@Service
public class MailConnection {

    @Value("${mail.host}")
    private String host;

    @Value("${mail.port}")
    private String port;

    private final String path;

    final RestTemplate restTemplate = new RestTemplate();

    private ObjectMapper objectMapper = new ObjectMapper();

    public boolean checkCode(String email, String code) throws JsonProcessingException {
        String request = path + "/mail/check_code/" + email + "/" + code;
        String response = restTemplate.getForObject(request, String.class);
        boolean result = objectMapper.readValue(response, Boolean.class);
        return result;
    }

    @Autowired
    public MailConnection(@Value("${mail.host}") final String host, @Value("${mail.port}") final String port) {
        this.host = host;
        this.port = port;
        this.path = "http://" + host + ":" + port;
    }
}
