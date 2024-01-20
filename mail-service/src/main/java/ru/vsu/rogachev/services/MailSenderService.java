package ru.vsu.rogachev.services;

import ru.vsu.rogachev.dto.MailParams;

public interface MailSenderService {
    void send(MailParams mailParams);
}
