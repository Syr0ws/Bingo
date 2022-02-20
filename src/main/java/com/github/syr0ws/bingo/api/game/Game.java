package com.github.syr0ws.bingo.api.game;

import com.github.syr0ws.bingo.api.game.controller.GameController;
import com.github.syr0ws.bingo.api.game.model.GameModel;
import com.github.syr0ws.bingo.api.tool.Observer;

public interface Game extends Observer {

    void start();

    void stop();

    GameModel getModel();

    GameController getController();
}
