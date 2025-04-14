package ru.vsu.rogachev.services;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.rogachev.entity.GameEvent;
import ru.vsu.rogachev.entity.GameParameters;
import ru.vsu.rogachev.entity.User;
import ru.vsu.rogachev.entity.enums.GameEventType;
import ru.vsu.rogachev.repositories.GameEventRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameEventService {

    private final GameEventRepository gameEventRepository;

    public void add(@NotNull GameEvent gameEvent){
        gameEventRepository.save(gameEvent);
    }

    public void createGame(@NotNull User originator, @NotNull GameParameters gameParameters){
        GameEvent gameEvent = new GameEvent(GameEventType.CREATE_GAME, originator);
        gameEvent.setGameParameters(gameParameters);
        add(gameEvent);
    }

    public void connectToGame(@NotNull User participant){
        GameEvent gameEvent = new GameEvent(GameEventType.JOIN_GAME, participant);
        add(gameEvent);
    }

    public void refuseGame(@NotNull User participant){
        GameEvent gameEvent = new GameEvent(GameEventType.REFUSE_GAME, participant);
        add(gameEvent);
    }

    @NotNull
    public List<GameEvent> getAll(){
        return gameEventRepository.findAll();
    }

    @Transactional
    public void delete(@NotNull GameEvent gameEvent){
        gameEventRepository.delete(gameEvent);
    }

}
