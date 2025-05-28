package ru.vsu.rogachev.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.vsu.rogachev.entity.GameLog;
import ru.vsu.rogachev.repository.GameLogRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameLogService {

    private final GameLogRepository gameLogRepository;

    public void save(@NotNull GameLog gameLog){
        gameLogRepository.save(gameLog);
    }

    @NotNull
    public List<GameLog> getAll(){
        return gameLogRepository.findAll();
    }

    @NotNull
    public List<GameLog> getAllByPlayersHandle(@NotNull String handle) {
        return gameLogRepository.findAllByPlayersHandle(handle);
    }

    @Transactional
    public void delete(@NotNull GameLog gameLog){
        gameLogRepository.delete(gameLog);
    }

}
