package ru.vsu.rogachev.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.vsu.rogachev.service.distributor.MessageDistributor;

@Log4j
@Component
@RequiredArgsConstructor
public class UpdateController {

    private final MessageDistributor messageDistributor;

    public void processUpdate(Update update){
        if(update == null){
            log.error("Received update is null");
            return;
        }

        if(update.getMessage() != null || update.hasCallbackQuery()){
            messageDistributor.distributeMessagesByType(update);
        }else{
            log.error("Unsupported message type is received: " + update);
        }
    }

}
