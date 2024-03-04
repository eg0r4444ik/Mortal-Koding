package ru.vsu.rogachev.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DbService {

    @Value("${db.host}")
    private String host;

    @Value("${db.port}")
    private String port;

    private String path = "http://" + host + ":" + port + "/confirm";

    final RestTemplate restTemplate = new RestTemplate();

    public void addConfirmRequest(User user, String activationCode){
        restTemplate.postForObject(path + "/add", new ConfirmDTO(user, activationCode), ConfirmDTO.class);
    }

    public ConfirmRequest getConfirmRequest(User user){
        ConfirmRequest response = restTemplate.getForObject(path + "/getByUserId/" + user.getId(), ConfirmRequest.class);
        return response;
    }

    public void deleteConfirmRequest(User user){
        restTemplate.getForObject(path + "/deleteById/" + user.getId(), ConfirmRequest.class);
    }
}
