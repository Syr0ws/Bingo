package com.github.syr0ws.bingo.api.minigame;

import com.github.syr0ws.bingo.api.game.Game;

import java.util.Collection;

public interface MiniGameModel {

    void setCurrentGame(Game game);

    Game getCurrentGame();

    void addGame(Game game);

    void removeGame(Game game);

    boolean hasGame(Game game);

    Collection<Game> getGames();
}
