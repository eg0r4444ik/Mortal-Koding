package ru.vsu.rogachev.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.vsu.rogachev.dto.PlayerDTO;
import ru.vsu.rogachev.entities.Player;
import ru.vsu.rogachev.dto.ProblemDTO;

import java.util.Set;

public interface PlayerService {

    Set<ProblemDTO> getPlayerProblems(String handle) throws InterruptedException, JsonProcessingException;

    Set<String> getPlayerProblemSet(String handle) throws InterruptedException, JsonProcessingException;

    PlayerDTO getPlayer(String handle) throws InterruptedException, JsonProcessingException;

    void add(Player player);

    void add(String handle, String email, long rating);

    void deleteByHandle(String handle);

    Player getByHandle(String handle);

    void setWaitingGame(String handle, long id);

    void setGameSession(String handle, long id);
}
