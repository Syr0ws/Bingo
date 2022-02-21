package com.github.syr0ws.bingo.api.game;

import com.github.syr0ws.bingo.api.game.controller.GameController;
import com.github.syr0ws.bingo.api.game.model.GameModel;
import com.github.syr0ws.bingo.api.message.Observable;
import com.github.syr0ws.bingo.api.message.Observer;
import org.bukkit.plugin.Plugin;

public interface Game extends Observable, Observer {

    void load();

    void unload();

    String getId();

    GameModel getModel();

    GameController getController();

    Plugin getPlugin();
}
