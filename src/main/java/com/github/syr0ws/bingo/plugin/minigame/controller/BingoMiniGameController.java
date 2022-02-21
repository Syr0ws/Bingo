package com.github.syr0ws.bingo.plugin.minigame.controller;

import com.github.syr0ws.bingo.api.game.Game;
import com.github.syr0ws.bingo.api.message.Message;
import com.github.syr0ws.bingo.api.message.MessageData;
import com.github.syr0ws.bingo.api.message.MessageType;
import com.github.syr0ws.bingo.api.minigame.MiniGameController;
import com.github.syr0ws.bingo.api.minigame.MiniGameModel;
import com.github.syr0ws.bingo.plugin.game.BingoGame;
import com.github.syr0ws.bingo.plugin.message.GameMessageKey;
import com.github.syr0ws.bingo.plugin.message.GameMessageType;
import com.github.syr0ws.bingo.plugin.minigame.listener.BingoMiniGameListener;
import com.github.syr0ws.bingo.plugin.tool.ListenerManager;
import com.github.syr0ws.bingo.plugin.tool.controller.AbstractController;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.UUID;

public class BingoMiniGameController extends AbstractController implements MiniGameController {

    private final MiniGameModel model;

    public BingoMiniGameController(Plugin plugin, MiniGameModel model) {
        super(plugin);

        if(model == null)
            throw new IllegalArgumentException("MiniGameModel cannot be null.");

        this.model = model;
    }

    @Override
    public void load() {
        super.load();
        this.loadWaitingGame();
    }

    @Override
    public void unload() {
        super.unload();
        this.unloadWaitingGame();
    }

    @Override
    public void registerListeners(ListenerManager manager) {
        manager.registerListener(new BingoMiniGameListener(this.model));
    }

    @Override
    public void onGameStart(Game game) {
        this.model.addGame(game);
        this.loadWaitingGame(); // Loading a new game.
    }

    @Override
    public void onGameStop(Game game) {
        this.model.removeGame(game);
        game.unload();
    }

    @Override
    public void onMessageReceiving(Message message) {

        MessageType type = message.getType();
        MessageData data = message.getData();

        if(type == GameMessageType.GAME_STARTED) {

            Game game = data.get(GameMessageKey.GAME.getKey(), Game.class);
            this.onGameStart(game);

        } else if(type == GameMessageType.GAME_FINISHED) {

            Game game = data.get(GameMessageKey.GAME.getKey(), Game.class);
            this.onGameStop(game);
        }
    }

    private void loadWaitingGame() {

        UUID uuid = UUID.randomUUID();

        BingoGame game = new BingoGame(super.getPlugin(), uuid.toString());
        game.load();

        this.model.setWaitingGame(game);
    }

    private void unloadWaitingGame() {

        Optional<Game> optional = this.model.getWaitingGame();

        if(optional.isEmpty()) return;

        Game game = optional.get();
        game.unload();

        this.model.setWaitingGame(null);
    }
}
