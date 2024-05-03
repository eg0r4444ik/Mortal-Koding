package ru.vsu.rogachev.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.rogachev.entities.dto.ConfirmRequestDTO;
import ru.vsu.rogachev.services.MailSenderService;

@RequestMapping("/mail")
@RestController
public class MailController {

    @Autowired
    MailSenderService mailSenderService;

    @PostMapping("/check_code")
    public boolean sendActivationMail(@RequestBody ConfirmRequestDTO confirmRequestDTO) throws JsonProcessingException {
        return mailSenderService.checkCode(confirmRequestDTO.getEmail(), confirmRequestDTO.getConfirmationCode());
    }

}
