package com.github.syr0ws.bingo.plugin.minigame.model;

import com.github.syr0ws.bingo.api.game.Game;
import com.github.syr0ws.bingo.api.minigame.MiniGameModel;
import com.github.syr0ws.bingo.api.settings.GameSettings;

import java.util.*;

public class BingoMiniGameModel implements MiniGameModel {

    private Game game;
    private final GameSettings settings;
    private final List<Game> games = new ArrayList<>();

    public BingoMiniGameModel(GameSettings settings) {

        if(settings == null)
            throw new IllegalArgumentException("GameSettings cannot be null.");

        this.settings = settings;
    }

    @Override
    public GameSettings getSettings() {
        return this.settings;
    }

    @Override
    public void setWaitingGame(Game game) {
        this.game = game;
    }

    @Override
    public Optional<Game> getWaitingGame() {
        return Optional.ofNullable(this.game);
    }

    @Override
    public void addGame(Game game) {

        if(game == null)
            throw new IllegalArgumentException("Game cannot be null.");

        if(this.hasGame(game.getId()))
            throw new IllegalArgumentException("Game already exists.");

        this.games.add(game);
    }

    @Override
    public void removeGame(Game game) {

        if(game == null)
            throw new IllegalArgumentException("Game cannot be null.");

        this.games.remove(game);
    }

    @Override
    public boolean hasGame(UUID uuid) {
        return this.games.stream()
                .map(Game::getModel)
                .anyMatch(model -> model.hasPlayer(uuid));
    }

    @Override
    public boolean hasGame(String id) {

        if(id == null || id.isEmpty())
            throw new IllegalArgumentException("Id cannot be null or empty.");

        return this.games.stream().anyMatch(game -> game.getId().equals(id));
    }

    @Override
    public Optional<Game> getGame(UUID uuid) {
        return this.games.stream()
                .filter(game -> game.getModel().hasPlayer(uuid))
                .findFirst();
    }

    @Override
    public Optional<Game> getGame(String id) {

        if(id == null || id.isEmpty())
            throw new IllegalArgumentException("Id cannot be null or empty.");

        return this.games.stream()
                .filter(game -> game.getId().equals(id))
                .findFirst();
    }

    @Override
    public Collection<Game> getGames() {
        return Collections.unmodifiableList(this.games);
    }
}
