package ru.vsu.rogachev.services;


import ru.vsu.rogachev.entities.ConfirmRequest;

public interface MailSenderService {
    void send(ConfirmRequest request);
}
