package ru.vsu.rogachev.services;

public interface MailSenderService {
    void send(String email);
    boolean checkCode(String email, String code);
}
