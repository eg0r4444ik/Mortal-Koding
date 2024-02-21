package ru.vsu.rogachev.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.entities.ConfirmRequest;
import ru.vsu.rogachev.generator.CodeGenerator;
import ru.vsu.rogachev.services.MailSenderService;

@Service
@RequiredArgsConstructor
public class MailServiceSenderImpl implements MailSenderService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Autowired
    private CodeGenerator generator;

    @Override
    public void send(ConfirmRequest request) {
        var subject = "Активация учетной записи";
        String activationCode = generator.generateActivationCode();
        var messageBody = getActivationMailBody(activationCode);
        var emailTo = request.getUser().getEmail();

        var mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(emailFrom);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(messageBody);

        javaMailSender.send(mailMessage);
    }

    private String getActivationMailBody(String code) {
        var msg = String.format("Для завершения регистрации введите полученный код в чат:\n%s",
                code);
        return msg;
    }
}
