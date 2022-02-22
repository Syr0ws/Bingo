package com.github.syr0ws.bingo.plugin.game;

import com.github.syr0ws.bingo.api.game.Game;
import com.github.syr0ws.bingo.api.game.controller.GameController;
import com.github.syr0ws.bingo.api.game.model.GameModel;
import com.github.syr0ws.bingo.api.game.model.GameState;
import com.github.syr0ws.bingo.api.message.Message;
import com.github.syr0ws.bingo.api.message.MessageData;
import com.github.syr0ws.bingo.api.message.MessageType;
import com.github.syr0ws.bingo.plugin.game.controller.BingoGameControllerFactory;
import com.github.syr0ws.bingo.plugin.game.model.BingoGameModel;
import com.github.syr0ws.bingo.plugin.message.*;
import com.github.syr0ws.bingo.plugin.tool.AbstractObservable;
import org.bukkit.plugin.Plugin;

import java.util.Optional;

public class BingoGame extends AbstractObservable implements Game {

    private final String id;
    private final Plugin plugin;
    private GameModel model;
    private GameController controller;

    public BingoGame(Plugin plugin, String id) {

        if(plugin == null)
            throw new IllegalArgumentException("Plugin cannot be null.");

        if(id == null || id.isEmpty())
            throw new IllegalArgumentException("Id cannot be null or empty.");

        this.plugin = plugin;
        this.id = id;
    }

    @Override
    public void onMessageReceiving(Message message) {

        MessageType type = message.getType();

        if(type == GameMessageType.CONTROLLER_DONE) this.setNextState();
    }

    @Override
    public void load() {
        this.setupModel();
        this.setupController(GameState.WAITING);
    }

    @Override
    public void unload() {
        this.unloadController();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public GameModel getModel() {
        return this.model;
    }

    @Override
    public GameController getController() {
        return this.controller;
    }

    @Override
    public Plugin getPlugin() {
        return this.plugin;
    }

    private void setupController(GameState state) {
        this.controller = BingoGameControllerFactory.getController(this, state);
        this.controller.load();
    }

    private void setupModel() {
        this.model = new BingoGameModel(null);
    }

    private void setNextState() {

        GameState current = this.controller.getState();
        Optional<GameState> optional =  GameState.getNext(current);

        // Unloading controller.
        this.unloadController();

        if(optional.isPresent()) {

            GameState next = optional.get();

            // Setting up controller for the new state.
            this.setupController(next);

        } else {

            GameMessageUtil.sendSimpleMessage(this, GameMessageType.GAME_FINISHED, GameMessageKey.GAME, Game.class, this);
        }
    }

    private void unloadController() {
        this.controller.unload();
        this.controller = null; // Avoid reuse.
    }
}
