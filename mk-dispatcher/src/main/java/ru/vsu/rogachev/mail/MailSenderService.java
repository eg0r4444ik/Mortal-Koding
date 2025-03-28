package ru.vsu.rogachev.mail;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.mail.generator.CodeGenerator;
import ru.vsu.rogachev.services.ConfirmService;

@Service
@RequiredArgsConstructor
public class MailSenderService {

    private static final String ACTIVATION_EMAIL_MESSAGE =
            "Для завершения регистрации введите полученный код в чат:\n%s";
    private static final String ACTIVATION_EMAIL_HEADER = "Активация учетной записи";

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;

    private final CodeGenerator generator;

    private final ConfirmService confirmService;

    public void send(@NotNull String email) {
        if(confirmService.existsByEmail(email)){
            confirmService.deleteByEmail(email);
        }

        String activationCode = generator.generateActivationCode();
        var messageBody = String.format(ACTIVATION_EMAIL_MESSAGE, activationCode);

        var mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(emailFrom);
        mailMessage.setTo(email);
        mailMessage.setSubject(ACTIVATION_EMAIL_HEADER);
        mailMessage.setText(messageBody);

        confirmService.add(email, activationCode);
        javaMailSender.send(mailMessage);
    }

    public boolean checkCode(@NotNull String email, @NotNull String code) {
        return confirmService.getByEmail(email)
                .orElseThrow(() -> new RuntimeException("Confirmation code not found"))
                .getConfirmationCode().equals(code);
    }

}
