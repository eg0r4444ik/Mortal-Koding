package ru.vsu.rogachev.services;

import ru.vsu.rogachev.entities.Player;
import ru.vsu.rogachev.models.Problem;

import java.util.Set;

public interface PlayerService {

    Set<Problem> getUserProblems(String handle)throws InterruptedException;
    Set<String> getUserProblemSet(String handle)throws InterruptedException;
    Player getUser(String handle)throws InterruptedException;

    void add(Player player);

    void add(String handle, String email, long rating);

    void deleteByHandle(String handle);

    Player getByHandle(String handle);

    void setWaitingGame(String handle, long id);

    void setGameSession(String handle, long id);
}
