package ru.vsu.rogachev.services.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import ru.vsu.rogachev.generator.CodeGenerator;
import ru.vsu.rogachev.services.ConfirmService;
import ru.vsu.rogachev.services.MailSenderService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MailSenderServiceImplTest {

    @Autowired
    private MailSenderServiceImpl mailSenderService;

    @Test
    void send() {
        mailSenderService.send("egorchik.rog@yandex.ru");
    }

}