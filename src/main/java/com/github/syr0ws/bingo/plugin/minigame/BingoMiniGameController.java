package com.github.syr0ws.bingo.plugin.minigame;

import com.github.syr0ws.bingo.api.game.Game;
import com.github.syr0ws.bingo.api.minigame.MiniGameController;
import com.github.syr0ws.bingo.api.minigame.MiniGameModel;
import com.github.syr0ws.bingo.api.tool.Change;
import com.github.syr0ws.bingo.api.tool.ChangeData;
import com.github.syr0ws.bingo.api.tool.ChangeType;
import com.github.syr0ws.bingo.plugin.game.BingoGame;

import java.util.Optional;
import java.util.UUID;

public class BingoMiniGameController implements MiniGameController {

    private final MiniGameModel model;
    private boolean loaded;

    public BingoMiniGameController(MiniGameModel model) {

        if(model == null)
            throw new IllegalArgumentException("MiniGameModel cannot be null.");

        this.model = model;
    }

    @Override
    public void load() {

        if(this.loaded)
            throw new IllegalStateException("Controller already loaded.");

        this.loadWaitingGame();
        this.loaded = true;
    }

    @Override
    public void unload() {

        if(!this.loaded)
            throw new IllegalStateException("Controller not loaded.");

        this.unloadWaitingGame();
        this.loaded = false;
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
    public void onChange(Change change) {

        ChangeType type = change.getType();
        ChangeData data = change.getData();

        if(type == BingoMiniGameChangeType.GAME_STARTED) {

            Game game = data.get(type.getKey(), Game.class);
            this.onGameStart(game);

        } else if(type == BingoMiniGameChangeType.GAME_FINISHED) {

            Game game = data.get(type.getKey(), Game.class);
            this.onGameStop(game);
        }
    }

    private void loadWaitingGame() {

        UUID uuid = UUID.randomUUID();

        BingoGame game = new BingoGame(uuid.toString());
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
