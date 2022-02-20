package com.github.syr0ws.bingo.api.game;

import com.github.syr0ws.bingo.api.game.controller.GameController;
import com.github.syr0ws.bingo.api.game.model.GameModel;
import com.github.syr0ws.bingo.api.tool.Observable;
import com.github.syr0ws.bingo.api.tool.Observer;

public interface Game extends Observable, Observer {

    void load();

    void unload();

    String getId();

    GameModel getModel();

    GameController getController();
}
