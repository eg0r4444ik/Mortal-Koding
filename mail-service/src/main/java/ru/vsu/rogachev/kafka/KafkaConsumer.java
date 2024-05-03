package ru.vsu.rogachev.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.services.MailSenderService;

@Service
public class KafkaConsumer {

    @Autowired
    MailSenderService mailSenderService;

    @KafkaListener(topics = "send-message-event-topic", groupId = "mail-confirm-events")
    public void sendMessage(String email){
        mailSenderService.send(email);
    }

}
