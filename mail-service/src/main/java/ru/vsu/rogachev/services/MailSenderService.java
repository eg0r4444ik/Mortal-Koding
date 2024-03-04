package ru.vsu.rogachev.services;

import ru.vsu.rogachev.db.dto.UserDTO;

public interface MailSenderService {
    void send(UserDTO user);
}
