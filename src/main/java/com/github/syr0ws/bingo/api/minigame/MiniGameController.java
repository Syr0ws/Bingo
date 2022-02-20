package com.github.syr0ws.bingo.api.minigame;

import com.github.syr0ws.bingo.api.game.Game;
import com.github.syr0ws.bingo.api.tool.Observer;

public interface MiniGameController extends Observer {

    void onGameStart(Game game);

    void onGameStop(Game game);
}
