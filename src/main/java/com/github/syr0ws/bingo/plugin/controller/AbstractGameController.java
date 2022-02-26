package com.github.syr0ws.bingo.plugin.controller;

import com.github.syr0ws.bingo.api.game.Game;
import com.github.syr0ws.bingo.api.game.controller.GameController;
import com.github.syr0ws.bingo.api.message.Message;
import com.github.syr0ws.bingo.api.message.MessageData;
import com.github.syr0ws.bingo.api.minigame.MiniGamePlugin;
import com.github.syr0ws.bingo.plugin.message.GameMessage;
import com.github.syr0ws.bingo.plugin.message.GameMessageKey;
import com.github.syr0ws.bingo.plugin.message.GameMessageType;
import org.bukkit.plugin.Plugin;

public abstract class AbstractGameController extends AbstractController implements GameController {

    private final Game game;

    public AbstractGameController(MiniGamePlugin plugin, Game game) {
        super(plugin);

        if(game == null)
            throw new IllegalArgumentException("Game cannot be null.");

        this.game = game;
    }

    public Game getGame() {
        return this.game;
    }

    protected void sendDoneMessage() {

        Message message = new GameMessage(GameMessageType.CONTROLLER_DONE);

        MessageData data = message.getData();
        data.set(GameMessageKey.CONTROLLER.getKey(), GameController.class, this);

        this.game.onMessageReceiving(message);
    }
}
