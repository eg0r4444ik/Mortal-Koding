package ru.vsu.rogachev.services;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.rogachev.entity.Game;
import ru.vsu.rogachev.repositories.GameRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    public void add(@NotNull Game game){
        gameRepository.save(game);
    }

    @NotNull
    public List<Game> getAll(){
        return gameRepository.findAll();
    }


    @NotNull
    public Optional<Game> getByPlayerHandle(@NotNull String handle) {
        return gameRepository.findByPlayerHandle(handle);
    }

    @Transactional
    public void delete(@NotNull Game game){
        gameRepository.delete(game);
    }

}
