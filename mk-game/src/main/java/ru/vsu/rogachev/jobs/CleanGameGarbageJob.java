package ru.vsu.rogachev.jobs;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.entity.enums.GameState;
import ru.vsu.rogachev.service.GameService;

@Log4j
@Service
@RequiredArgsConstructor
public class CleanGameGarbageJob {

    private final GameService gameService;

    @Scheduled(fixedRate = 5000)
    public void removeGarbage() {
        gameService.deleteAllByState(GameState.FINISHED);
    }

}
