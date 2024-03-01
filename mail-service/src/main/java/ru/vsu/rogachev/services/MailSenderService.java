package ru.vsu.rogachev.services;

import ru.vsu.rogachev.entities.User;

public interface MailSenderService {
    void send(User user);
}
