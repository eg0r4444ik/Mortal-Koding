package ru.vsu.rogachev.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.vsu.rogachev.dto.PlayerDTO;
import ru.vsu.rogachev.entities.Player;
import ru.vsu.rogachev.dto.ProblemDTO;
import ru.vsu.rogachev.entities.enums.PlayerState;

import java.util.Set;

public interface PlayerService {

    Set<ProblemDTO> getPlayerProblems(String handle) throws InterruptedException, JsonProcessingException;

    Set<String> getPlayerProblemSet(String handle) throws InterruptedException, JsonProcessingException;

    PlayerDTO getPlayer(String handle) throws InterruptedException, JsonProcessingException;

    void add(Player player);

    void add(String handle, String email, long rating, PlayerState state);

    void deleteByHandle(String handle);

    Player getByHandle(String handle);

    void setGameSession(String handle, long id);
}
