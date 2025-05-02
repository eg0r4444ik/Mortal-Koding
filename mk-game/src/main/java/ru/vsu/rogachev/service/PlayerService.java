package ru.vsu.rogachev.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.entity.Player;
import ru.vsu.rogachev.repository.PlayerRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    public void add(@NotNull Player player) {
        playerRepository.save(player);
    }

    public @NotNull Optional<Player> getByHandle(@NotNull String handle) {
        return playerRepository.findByHandle(handle);
    }

    public void deleteByHandle(@NotNull String handle) {
        playerRepository.deleteByHandle(handle);
    }

}
