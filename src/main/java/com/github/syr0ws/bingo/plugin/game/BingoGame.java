package com.github.syr0ws.bingo.plugin.game;

import com.github.syr0ws.bingo.api.game.Game;
import com.github.syr0ws.bingo.api.game.controller.GameController;
import com.github.syr0ws.bingo.api.game.model.GameGrid;
import com.github.syr0ws.bingo.api.game.model.GameGridGenerator;
import com.github.syr0ws.bingo.api.game.model.GameModel;
import com.github.syr0ws.bingo.api.game.model.GameState;
import com.github.syr0ws.bingo.api.message.Message;
import com.github.syr0ws.bingo.api.message.MessageData;
import com.github.syr0ws.bingo.api.message.MessageType;
import com.github.syr0ws.bingo.api.minigame.MiniGameModel;
import com.github.syr0ws.bingo.api.minigame.MiniGamePlugin;
import com.github.syr0ws.bingo.api.settings.GameSettings;
import com.github.syr0ws.bingo.api.settings.MutableSetting;
import com.github.syr0ws.bingo.plugin.game.controller.BingoGameControllerFactory;
import com.github.syr0ws.bingo.plugin.game.model.BingoGameModel;
import com.github.syr0ws.bingo.plugin.game.model.DefaultGameGridGenerator;
import com.github.syr0ws.bingo.plugin.message.GameMessageKey;
import com.github.syr0ws.bingo.plugin.message.GameMessageType;
import com.github.syr0ws.bingo.plugin.message.GameMessageUtil;
import com.github.syr0ws.bingo.plugin.tool.AbstractObservable;
import org.bukkit.Material;

import java.util.List;
import java.util.Optional;

public class BingoGame extends AbstractObservable implements Game {

    private final String id;
    private final MiniGamePlugin plugin;
    private GameModel model;
    private GameController controller;

    public BingoGame(MiniGamePlugin plugin, String id) {

        if(plugin == null)
            throw new IllegalArgumentException("MiniGamePlugin cannot be null.");

        if(id == null || id.isEmpty())
            throw new IllegalArgumentException("Id cannot be null or empty.");

        this.plugin = plugin;
        this.id = id;
    }

    @Override
    public void onMessageReceiving(Message message) {

        MessageType type = message.getType();
        MessageData data = message.getData();

        if(type == GameMessageType.CONTROLLER_DONE) {

            this.setNextState();

        } else if(type == GameMessageType.GAME_STATE_CHANGE) {

            GameState state = data.get(GameMessageKey.GAME_STATE.getKey(), GameState.class);

            if(state != this.controller.getState()) this.changeState(state);
        }
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
    public MiniGamePlugin getPlugin() {
        return this.plugin;
    }

    private void setupController(GameState state) {

        this.controller = BingoGameControllerFactory.getController(this, state);
        this.controller.load();

        this.model.setState(state);
        this.model.addObserver(this.controller);
    }

    private void setupModel() {

        MiniGameModel model = this.plugin.getModel();
        GameSettings settings = model.getSettings();

        MutableSetting<List<Material>> setting = settings.getBannedItems();

        GameGridGenerator generator = new DefaultGameGridGenerator();
        GameGrid gameGrid = generator.generate(DefaultGameGridGenerator.DEFAULT_GRID_SIZE, setting.getValue());

        this.model = new BingoGameModel(gameGrid, this.plugin.getModel().getSettings());
    }

    private void setNextState() {

        GameState current = this.controller.getState();
        Optional<GameState> optional =  GameState.getNext(current);

        if(optional.isPresent()) {

            GameState state = optional.get();

            this.changeState(state);

        } else {

            GameMessageUtil.sendSimpleMessage(this, GameMessageType.GAME_FINISHED, GameMessageKey.GAME, Game.class, this);
        }
    }

    private void changeState(GameState state) {

        // Unloading controller.
        this.unloadController();

        // Setting up controller for the new state.
        this.setupController(state);
    }

    private void unloadController() {

        if(this.controller == null || !this.controller.isLoaded()) return;

        this.controller.unload();
        this.controller = null; // Avoid reuse.
    }
}
