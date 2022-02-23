package com.github.syr0ws.bingo.plugin.minigame.controller;

import com.github.syr0ws.bingo.api.game.Game;
import com.github.syr0ws.bingo.api.game.model.GameModel;
import com.github.syr0ws.bingo.api.message.Message;
import com.github.syr0ws.bingo.api.message.MessageData;
import com.github.syr0ws.bingo.api.message.MessageType;
import com.github.syr0ws.bingo.api.minigame.MiniGameController;
import com.github.syr0ws.bingo.api.minigame.MiniGameModel;
import com.github.syr0ws.bingo.api.minigame.MiniGamePlugin;
import com.github.syr0ws.bingo.plugin.controller.AbstractController;
import com.github.syr0ws.bingo.plugin.game.BingoGame;
import com.github.syr0ws.bingo.plugin.message.GameMessage;
import com.github.syr0ws.bingo.plugin.message.GameMessageKey;
import com.github.syr0ws.bingo.plugin.message.GameMessageType;
import com.github.syr0ws.bingo.plugin.minigame.listener.BingoMiniGameListener;
import com.github.syr0ws.bingo.plugin.tool.ListenerManager;
import com.github.syr0ws.bingo.plugin.tool.Text;

import java.util.Optional;
import java.util.UUID;

public class BingoMiniGameController extends AbstractController implements MiniGameController {

    private final MiniGameModel model;

    public BingoMiniGameController(MiniGamePlugin plugin, MiniGameModel model) {
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
        manager.registerListener(new BingoMiniGameListener(this.getPlugin()));
    }

    @Override
    public void onGameStart(Game game) {

        this.model.addGame(game);

        Message message = new GameMessage(GameMessageType.START_GAME);
        game.getController().onMessageReceiving(message);

        this.loadWaitingGame(); // Loading a new game.
    }

    @Override
    public void onGameStop(Game game) {

        GameModel model = game.getModel();
        model.getOnlinePlayers().forEach(player -> player.kickPlayer(Text.GAME_FINISHED.get()));

        game.removeObserver(this);
        game.unload();

        this.model.removeGame(game);
    }

    @Override
    public MiniGamePlugin getPlugin() {
        return super.getPlugin();
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
        game.addObserver(this);
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
