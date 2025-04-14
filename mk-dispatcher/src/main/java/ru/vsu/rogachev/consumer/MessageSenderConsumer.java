package ru.vsu.rogachev.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.dto.SendMessageEvent;
import ru.vsu.rogachev.utils.MessageUtils;

@Log4j
@Service
@RequiredArgsConstructor
public class MessageSenderConsumer {

    private final MessageUtils messageUtils;

    @KafkaListener(topics = "send-message-event-topic", groupId = "dispatcher-events")
    public void processSendMessageEvent(SendMessageEvent event){
        event.getChatIds().forEach(chatId -> messageUtils.sendMessage(chatId, event.getMessage()));
    }

}
