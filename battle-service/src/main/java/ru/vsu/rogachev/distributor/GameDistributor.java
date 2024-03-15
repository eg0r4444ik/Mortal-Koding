package ru.vsu.rogachev.distributor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.rogachev.entities.GameSession;
import ru.vsu.rogachev.entities.Player;
import ru.vsu.rogachev.entities.enums.PlayerState;
import ru.vsu.rogachev.services.GameSessionService;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameDistributor {

    @Autowired
    private GameSessionService gameSessionService;

    public void distribute(Player player, long time, long playersCount){
        List<GameSession> games = gameSessionService.getAll();

        for(GameSession game : games){
            if(game.getPlayersCount() == playersCount && game.getTime() == time && !game.isStarted() &&
                    game.getPlayersCount() != game.getPlayers().size() && !haveNotActivePlayers(game)){
                gameSessionService.addPlayer(game, player);
                return;
            }
        }

        GameSession game = new GameSession(time, playersCount);
        game.addPlayer(player);
        gameSessionService.add(game);
    }

    private boolean haveNotActivePlayers(GameSession gameSession){
        for(Player player : gameSession.getPlayers()){
            if(player.getState() == PlayerState.NOT_CONNECTED){
                return true;
            }
        }

        return false;
    }

}
