package ru.vsu.rogachev.services;

import ru.vsu.rogachev.entities.WaitingGame;

public interface WaitingGameService {

    void add(WaitingGame waitingGame);

    WaitingGame getById(Long id);

    void deleteById(Long id);

}
