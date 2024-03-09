package ru.vsu.rogachev.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.vsu.rogachev.entities.WaitingGame;
import ru.vsu.rogachev.repositories.WaitingGameRepository;
import ru.vsu.rogachev.services.WaitingGameService;

public class WaitingGameServiceImpl implements WaitingGameService {

    @Autowired
    private WaitingGameRepository waitingGameRepository;

    public void add(WaitingGame waitingGame) {
        waitingGameRepository.save(waitingGame);
    }

    public WaitingGame getById(Long id) {
        return waitingGameRepository.getById(id);
    }

    public void deleteById(Long id) {
        waitingGameRepository.deleteById(id);
    }
}
