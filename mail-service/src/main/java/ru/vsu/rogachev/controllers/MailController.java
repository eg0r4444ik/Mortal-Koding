package ru.vsu.rogachev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.rogachev.entities.ConfirmRequest;
import ru.vsu.rogachev.services.MailSenderService;

@RequestMapping("/mail")
@RestController
public class MailController {

    @Autowired
    MailSenderService mailSenderService;

    @PostMapping("/send")
    public ResponseEntity<?> sendActivationMail(@RequestBody ConfirmRequest request){
        mailSenderService.send(request);
        return ResponseEntity.ok().build();
    }

}
