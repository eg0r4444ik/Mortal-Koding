package ru.vsu.rogachev.auth.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.auth.utils.CodeGenerator;
import ru.vsu.rogachev.client.mk.auth.dto.CheckCodeRequest;
import ru.vsu.rogachev.client.mk.container.ResponseContainer;

@Service
@RequiredArgsConstructor
public class MailSenderService {

    private static final String ACTIVATION_EMAIL_HEADER = "Активация учетной записи";
    private static final String ACTIVATION_EMAIL_MESSAGE = "Для завершения регистрации введите полученный код в чат:\n%s";

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;

    private final ConfirmService confirmService;

    public ResponseContainer<Void> sendCode(@NotNull String email) {
        if(confirmService.existsByEmail(email)){
            confirmService.deleteByEmail(email);
        }

        String activationCode = CodeGenerator.generateActivationCode();
        var messageBody = String.format(ACTIVATION_EMAIL_MESSAGE, activationCode);

        var mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(emailFrom);
        mailMessage.setTo(email);
        mailMessage.setSubject(ACTIVATION_EMAIL_HEADER);
        mailMessage.setText(messageBody);

        confirmService.add(email, activationCode);
        javaMailSender.send(mailMessage);

        return ResponseContainer.success(null);
    }

    public ResponseContainer<Boolean> checkCode(@NotNull CheckCodeRequest request) {
        return ResponseContainer.success(
                confirmService.getByEmail(request.getEmail())
                        .orElseThrow(() -> new RuntimeException("Confirmation code not found"))
                        .getConfirmationCode().equals(request.getCode())
        );
    }

}
