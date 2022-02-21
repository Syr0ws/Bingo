package com.github.syr0ws.bingo.plugin.tool.controller;

import com.github.syr0ws.bingo.api.game.controller.GameController;
import com.github.syr0ws.bingo.api.game.model.GameModel;
import org.bukkit.plugin.Plugin;

public abstract class AbstractGameController extends AbstractController implements GameController {

    private final GameModel model;

    public AbstractGameController(Plugin plugin, GameModel model) {
        super(plugin);

        if(model == null)
            throw new IllegalArgumentException("GameModel cannot be null.");

        this.model = model;
    }

    public GameModel getModel() {
        return this.model;
    }
}
