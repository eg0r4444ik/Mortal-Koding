package ru.vsu.rogachev.auth.jobs;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.auth.service.ConfirmService;

import java.time.LocalDateTime;

@Log4j
@Service
@RequiredArgsConstructor
public class CleanAuthGarbageJob {

    private final ConfirmService confirmService;

    private static final Integer MINUTES_FOR_CONFIRM = 5;

    @Scheduled(fixedRate = 5000)
    public void removeGarbage() {
        LocalDateTime expirationTime = LocalDateTime.now().minusMinutes(MINUTES_FOR_CONFIRM);
        confirmService.deleteAllExpiredSince(expirationTime);
    }

}
