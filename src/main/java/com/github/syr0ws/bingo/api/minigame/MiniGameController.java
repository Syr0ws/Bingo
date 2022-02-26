package com.github.syr0ws.bingo.api.minigame;

import com.github.syr0ws.bingo.api.game.Game;
import com.github.syr0ws.bingo.api.game.controller.Controller;
import com.github.syr0ws.bingo.api.game.exception.GameException;
import com.github.syr0ws.bingo.api.message.Observer;

public interface MiniGameController extends Controller, Observer {

    void onGameStart(Game game) throws GameException;

    void onGameStop(Game game);
}
