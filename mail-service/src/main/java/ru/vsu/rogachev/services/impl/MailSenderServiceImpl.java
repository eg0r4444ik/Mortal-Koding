package ru.vsu.rogachev.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.generator.CodeGenerator;
import ru.vsu.rogachev.services.ConfirmService;
import ru.vsu.rogachev.services.MailSenderService;

@Service
@RequiredArgsConstructor
public class MailSenderServiceImpl implements MailSenderService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;

    private final CodeGenerator generator;

    private final ConfirmService confirmService;

    @Override
    public void send(String email) {
        if(confirmService.existsByEmail(email)){
            confirmService.deleteByEmail(email);
        }

        var subject = "Активация учетной записи";
        String activationCode = generator.generateActivationCode();
        var messageBody = getActivationMailBody(activationCode);

        var mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(emailFrom);
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(messageBody);

        confirmService.add(email, activationCode);
        javaMailSender.send(mailMessage);
    }

    @Override
    public boolean checkCode(String email, String code) {
        if(confirmService.existsByEmail(email) &&
                confirmService.getByEmail(email).getConfirmationCode().equals(code)){
            return true;
        }

        return false;
    }

    private String getActivationMailBody(String code) {
        var msg = String.format("Для завершения регистрации введите полученный код в чат:\n%s",
                code);
        return msg;
    }
}
