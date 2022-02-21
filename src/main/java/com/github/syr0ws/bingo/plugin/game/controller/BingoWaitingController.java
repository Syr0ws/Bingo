package com.github.syr0ws.bingo.plugin.game.controller;

import com.github.syr0ws.bingo.api.game.model.GameModel;
import com.github.syr0ws.bingo.api.game.model.GameState;
import com.github.syr0ws.bingo.api.message.Message;
import com.github.syr0ws.bingo.plugin.game.listener.GameWaitingListener;
import com.github.syr0ws.bingo.plugin.tool.ListenerManager;
import com.github.syr0ws.bingo.plugin.tool.controller.AbstractGameController;
import org.bukkit.plugin.Plugin;

public class BingoWaitingController extends AbstractGameController {

    public BingoWaitingController(Plugin plugin, GameModel model) {
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
        manager.registerListener(new GameWaitingListener(super.getModel()));
    }

    @Override
    public GameState getState() {
        return GameState.WAITING;
    }

    @Override
    public void onMessageReceiving(Message message) {

    }
}
