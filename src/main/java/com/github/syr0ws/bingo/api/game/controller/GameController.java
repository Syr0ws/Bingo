package com.github.syr0ws.bingo.api.game.controller;

import com.github.syr0ws.bingo.api.game.model.GameState;
import com.github.syr0ws.bingo.api.tool.Observer;

public interface GameController extends Observer {

    void load();

    void unload();

    GameState getState();
}
