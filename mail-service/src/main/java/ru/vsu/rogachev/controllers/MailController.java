package ru.vsu.rogachev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.vsu.rogachev.services.MailSenderService;

@RequestMapping("/mail")
@RestController
public class MailController {

    @Autowired
    MailSenderService mailSenderService;

    @GetMapping("/check_code/{email}/{code}")
    public boolean sendActivationMail(@PathVariable(value = "email") String email, @PathVariable(value = "code") String code){
        return mailSenderService.checkCode(email, code);
    }

}
