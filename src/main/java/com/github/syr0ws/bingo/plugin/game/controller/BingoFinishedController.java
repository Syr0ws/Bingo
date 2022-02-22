package com.github.syr0ws.bingo.plugin.game.controller;

import com.github.syr0ws.bingo.api.game.Game;
import com.github.syr0ws.bingo.api.game.model.GameState;
import com.github.syr0ws.bingo.api.message.Message;
import com.github.syr0ws.bingo.plugin.controller.AbstractGameController;
import org.bukkit.plugin.Plugin;

public class BingoFinishedController extends AbstractGameController {

    public BingoFinishedController(Plugin plugin, Game game) {
        super(plugin, game);
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
    public GameState getState() {
        return GameState.FINISHED;
    }

    @Override
    public void onMessageReceiving(Message message) {

    }
}
