package ru.vsu.rogachev.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.db.DbService;
import ru.vsu.rogachev.db.dto.ConfirmDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Log4j
@Service
public class Scheduler {

    @Autowired
    private DbService dbService;

    private final Long timeForConfirm = 300000L;

    @Scheduled(fixedRate = 5000)
    public void removeGarbage() throws JsonProcessingException {

        List<ConfirmDTO> confirms = dbService.getAll();
        List<ConfirmDTO> toDelete = new ArrayList<>();
        for(ConfirmDTO confirm : confirms){
            Date currentDate = new Date();
            Long ms = currentDate.getTime() - confirm.getCreationDate().getTime();
            if(ms >= timeForConfirm){
                toDelete.add(confirm);
            }
        }

        for(ConfirmDTO confirm : toDelete){
            dbService.deleteConfirmRequest(confirm.getUser());
            log.trace("delete confirm: " + confirm.getId() + " " + confirm.getConfirmationCode() + " " +
                    confirm.getUser().getEmail());
        }

    }

}
