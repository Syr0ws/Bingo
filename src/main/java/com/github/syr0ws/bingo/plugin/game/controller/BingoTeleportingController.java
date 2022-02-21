package com.github.syr0ws.bingo.plugin.game.controller;

import com.github.syr0ws.bingo.api.game.model.GameModel;
import com.github.syr0ws.bingo.api.game.model.GameState;
import com.github.syr0ws.bingo.api.message.Message;
import com.github.syr0ws.bingo.plugin.game.listener.GameTeleportingListener;
import com.github.syr0ws.bingo.plugin.tool.ListenerManager;
import com.github.syr0ws.bingo.plugin.tool.controller.AbstractGameController;
import org.bukkit.plugin.Plugin;

public class BingoTeleportingController extends AbstractGameController {

    public BingoTeleportingController(Plugin plugin, GameModel model) {
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
        manager.registerListener(new GameTeleportingListener(super.getModel()));
    }

    @Override
    public GameState getState() {
        return GameState.TELEPORTING;
    }

    @Override
    public void onMessageReceiving(Message message) {

    }
}
