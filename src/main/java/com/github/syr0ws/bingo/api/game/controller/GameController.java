package com.github.syr0ws.bingo.api.game.controller;

import com.github.syr0ws.bingo.api.game.model.GameState;
import com.github.syr0ws.bingo.api.message.Observer;

public interface GameController extends Controller, Observer {

    GameState getState();
}
