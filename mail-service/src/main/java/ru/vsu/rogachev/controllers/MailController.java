package ru.vsu.rogachev.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.rogachev.entities.User;
import ru.vsu.rogachev.services.MailSenderService;

@RequestMapping("/mail")
@RestController
public class MailController {

    @Autowired
    MailSenderService mailSenderService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/send")
    public ResponseEntity<?> sendActivationMail(@RequestBody String userJSON) throws JsonProcessingException {
        User user = objectMapper.readValue(userJSON, User.class);
        mailSenderService.send(user);
        return ResponseEntity.ok().build();
    }

}
