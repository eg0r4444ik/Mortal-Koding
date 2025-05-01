package ru.vsu.rogachev.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.service.MessageSender;

@Log4j
@Service
@RequiredArgsConstructor
public class MessageSenderConsumer {

    private final MessageSender messageSender;

//    @KafkaListener(topics = "send-message-event-topic", groupId = "dispatcher-events")
//    public void processSendMessageEvent(SendMessageEvent event){
//        event.getChatIds().forEach(chatId -> messageSender.sendMessage(chatId, event.getMessage()));
//    }

}
