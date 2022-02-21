package com.github.syr0ws.bingo.plugin.game.controller;

import com.github.syr0ws.bingo.api.game.model.GameModel;
import com.github.syr0ws.bingo.api.game.model.GameState;
import com.github.syr0ws.bingo.api.message.Message;
import com.github.syr0ws.bingo.plugin.game.listener.GameRunningListener;
import com.github.syr0ws.bingo.plugin.tool.ListenerManager;
import com.github.syr0ws.bingo.plugin.tool.controller.AbstractGameController;
import org.bukkit.plugin.Plugin;

public class BingoRunningController extends AbstractGameController {

    public BingoRunningController(Plugin plugin, GameModel model) {
        super(plugin, model);
    }

    @Override
    public void load() {
        super.load();
    }

    @Override
    public void unload() {
        super.unload();
    }

    @Override
    public void registerListeners(ListenerManager manager) {
        manager.registerListener(new GameRunningListener(super.getModel()));
    }

    @Override
    public GameState getState() {
        return GameState.RUNNING;
    }

    @Override
    public void onMessageReceiving(Message message) {

    }
}
