package com.github.syr0ws.bingo.plugin.game.controller;

import com.github.syr0ws.bingo.api.game.controller.GameController;
import com.github.syr0ws.bingo.api.game.model.GameState;
import com.github.syr0ws.bingo.api.tool.Change;

public class BingoFinishedController extends AbstractController implements GameController {

    @Override
    public void load() {
        super.load();
    }

    @Override
    public void unload() {
        super.unload();
    }

    @Override
    public GameState getState() {
        return GameState.FINISHED;
    }

    @Override
    public void onChange(Change change) {

    }
}
