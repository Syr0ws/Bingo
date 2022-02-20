package com.github.syr0ws.bingo.api.minigame;

import com.github.syr0ws.bingo.api.game.Game;

import java.util.Collection;
import java.util.Optional;

public interface MiniGameModel {

    void setWaitingGame(Game game);

    Optional<Game> getWaitingGame();

    void addGame(Game game);

    void removeGame(Game game);

    boolean hasGame(String id);

    Optional<Game> getGame(String id);

    Collection<Game> getGames();
}