package ru.vsu.rogachev.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.rogachev.auth.service.MailSenderService;
import ru.vsu.rogachev.client.mk.auth.dto.CheckCodeRequest;
import ru.vsu.rogachev.client.mk.container.ResponseContainer;

import static ru.vsu.rogachev.client.mk.auth.AuthEndpoints.CHECK_CODE_ENDPOINT;
import static ru.vsu.rogachev.client.mk.auth.AuthEndpoints.SEND_CODE_ENDPOINT;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final MailSenderService mailSenderService;

    @PostMapping(SEND_CODE_ENDPOINT)
    public ResponseContainer<Void> sendCode(@RequestBody String email){
        return mailSenderService.sendCode(email);
    }

    @PostMapping(CHECK_CODE_ENDPOINT)
    public ResponseContainer<Boolean> checkCode(@RequestBody CheckCodeRequest request){
        return mailSenderService.checkCode(request);
    }

}
