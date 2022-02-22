package com.github.syr0ws.bingo.plugin.game.controller;

import com.github.syr0ws.bingo.api.game.Game;
import com.github.syr0ws.bingo.api.game.model.GameModel;
import com.github.syr0ws.bingo.api.game.model.GameState;
import com.github.syr0ws.bingo.api.message.Message;
import com.github.syr0ws.bingo.plugin.game.listener.GameRunningListener;
import com.github.syr0ws.bingo.plugin.tool.ListenerManager;
import com.github.syr0ws.bingo.plugin.controller.AbstractGameController;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class BingoRunningController extends AbstractGameController {

    public BingoRunningController(Plugin plugin, Game game) {
        super(plugin, game);
    }

    @Override
    public void load() {
        super.load();
        Bukkit.broadcastMessage("Running");
    }

    @Override
    public void unload() {
        super.unload();
    }

    @Override
    public void registerListeners(ListenerManager manager) {

        GameModel model = super.getGame().getModel();

        manager.registerListener(new GameRunningListener(model));
    }

    @Override
    public GameState getState() {
        return GameState.RUNNING;
    }

    @Override
    public void onMessageReceiving(Message message) {

    }
}
