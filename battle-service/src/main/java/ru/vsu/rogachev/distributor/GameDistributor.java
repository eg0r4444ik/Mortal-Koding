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

    public void distribute(Player player){
        List<GameSession> games = gameSessionService.getAll();

        for(GameSession game : games){
            if(!game.isStarted() && game.getPlayersCount() != game.getPlayers().size()
                    && !haveNotActivePlayers(game)){
                gameSessionService.addPlayer(game, player);
                return;
            }
        }
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
