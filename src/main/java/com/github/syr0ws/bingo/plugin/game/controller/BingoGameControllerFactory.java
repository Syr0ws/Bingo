package com.github.syr0ws.bingo.plugin.game.controller;

import com.github.syr0ws.bingo.api.game.Game;
import com.github.syr0ws.bingo.api.game.controller.GameController;
import com.github.syr0ws.bingo.api.game.model.GameModel;
import com.github.syr0ws.bingo.api.game.model.GameState;
import com.github.syr0ws.bingo.api.minigame.MiniGamePlugin;
import org.bukkit.plugin.Plugin;

public class BingoGameControllerFactory {

    public static GameController getController(Game game, GameState state) {

        MiniGamePlugin plugin = game.getPlugin();

        switch (state) {
            case WAITING -> {
                return new BingoWaitingController(plugin, game);
            }
            case TELEPORTING -> {
                return new BingoTeleportingController(plugin, game);
            }
            case RUNNING -> {
                return new BingoRunningController(plugin, game);
            }
            case FINISHED -> {
                return new BingoFinishedController(plugin, game);
            }
            default -> throw new IllegalArgumentException(String.format("No controller found for GameState %s.", state.name()));
        }
    }
}
