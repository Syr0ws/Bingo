package com.github.syr0ws.bingo.plugin.game;

import com.github.syr0ws.bingo.api.game.Game;
import com.github.syr0ws.bingo.api.game.controller.GameController;
import com.github.syr0ws.bingo.api.game.model.GameModel;
import com.github.syr0ws.bingo.api.game.model.GameState;
import com.github.syr0ws.bingo.api.tool.Change;
import com.github.syr0ws.bingo.api.tool.ChangeData;
import com.github.syr0ws.bingo.api.tool.ChangeType;
import com.github.syr0ws.bingo.plugin.game.controller.BingoGameControllerFactory;
import com.github.syr0ws.bingo.plugin.minigame.BingoMiniGameChangeType;
import com.github.syr0ws.bingo.plugin.tool.AbstractObservable;
import com.github.syr0ws.bingo.plugin.tool.CommonChange;
import com.github.syr0ws.bingo.plugin.tool.CommonChangeData;

import java.util.Optional;

public class BingoGame extends AbstractObservable implements Game {

    private final String id;
    private GameModel model;
    private GameController controller;

    public BingoGame(String id) {

        if(id == null || id.isEmpty())
            throw new IllegalArgumentException("Id cannot be null or empty.");

        this.id = id;
    }

    @Override
    public void load() {

    }

    @Override
    public void unload() {

    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public GameModel getModel() {
        return null;
    }

    @Override
    public GameController getController() {
        return null;
    }

    @Override
    public void onChange(Change change) {

        ChangeType type = change.getType();
        ChangeData data = change.getData();

        if(type == BingoGameChangeType.DONE) {

            this.setNextState();
        }
    }

    private void setNextState() {

        GameState current = this.model.getState();
        Optional<GameState> optional =  GameState.getNext(current);

        // TODO To put in another method.
        this.controller.unload();
        this.controller = null;

        if(optional.isPresent()) {

            GameState next = optional.get();

            // TODO To put in another method.
            this.controller = BingoGameControllerFactory.getController(next);
            this.controller.load();

        } else {

            ChangeType type = BingoMiniGameChangeType.GAME_FINISHED;

            ChangeData data = new CommonChangeData();
            data.set(type.getKey(), Game.class, this);

            CommonChange change = new CommonChange(type, data);

            super.notifyChange(change);
        }
    }

    private void loadModel() {

    }
}
