package ru.vsu.rogachev.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.vsu.rogachev.db.dto.ConfirmDTO;
import ru.vsu.rogachev.db.dto.UserDTO;

@Service
public class DbService {

    @Value("${db.host}")
    private String host;

    @Value("${db.port}")
    private String port;

    private final String path;

    final RestTemplate restTemplate = new RestTemplate();

    public void addConfirmRequest(UserDTO user, String activationCode){
        restTemplate.postForObject(path + "/add", new ConfirmDTO(user, activationCode), ConfirmDTO.class);
    }

    public ConfirmDTO getConfirmRequest(UserDTO user){
        ConfirmDTO response = restTemplate.getForObject(path + "/getByUserId/" + user.getId(), ConfirmDTO.class);
        return response;
    }

    public void deleteConfirmRequest(UserDTO user){
        restTemplate.getForObject(path + "/deleteById/" + user.getId(), ConfirmDTO.class);
    }

    @Autowired
    public DbService(@Value("${db.host}") final String host, @Value("${db.port}") final String port) {
        this.host = host;
        this.port = port;
        this.path = "http://" + host + ":" + port + "/confirm";
    }
}
