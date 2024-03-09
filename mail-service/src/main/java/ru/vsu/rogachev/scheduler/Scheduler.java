package ru.vsu.rogachev.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.entities.ConfirmRequest;
import ru.vsu.rogachev.services.ConfirmService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Log4j
@Service
public class Scheduler {

    @Autowired
    private ConfirmService confirmService;

    private final Long timeForConfirm = 300000L;

    @Scheduled(fixedRate = 5000)
    public void removeGarbage() throws JsonProcessingException {

        List<ConfirmRequest> confirms = confirmService.getAll();
        List<ConfirmRequest> toDelete = new ArrayList<>();
        for(ConfirmRequest confirm : confirms){
            Date currentDate = new Date();
            Long ms = currentDate.getTime() - confirm.getCreationDate().getTime();
            if(ms >= timeForConfirm){
                toDelete.add(confirm);
            }
        }

        for(ConfirmRequest confirm : toDelete){
            confirmService.deleteById(confirm.getId());
            log.trace("delete confirm: " + confirm.getId() + " " + confirm.getConfirmationCode() + " " +
                    confirm.getEmail());
        }

    }

}
