package com.github.syr0ws.bingo.plugin.game.controller;

import com.github.syr0ws.bingo.api.game.Game;
import com.github.syr0ws.bingo.api.game.controller.GameController;
import com.github.syr0ws.bingo.api.game.model.GameModel;
import com.github.syr0ws.bingo.api.game.model.GameState;
import org.bukkit.plugin.Plugin;

public class BingoGameControllerFactory {

    public static GameController getController(Game game, GameState state) {

        Plugin plugin = game.getPlugin();
        GameModel model = game.getModel();

        switch (state) {
            case WAITING -> {
                return new BingoWaitingController(plugin, model);
            }
            case TELEPORTING -> {
                return new BingoTeleportingController(plugin, model);
            }
            case RUNNING -> {
                return new BingoRunningController(plugin, model);
            }
            case FINISHED -> {
                return new BingoFinishedController(plugin, model);
            }
            default -> throw new IllegalArgumentException(String.format("No controller found for GameState %s.", state.name()));
        }
    }
}
