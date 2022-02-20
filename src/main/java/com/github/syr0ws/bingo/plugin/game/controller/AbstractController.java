package com.github.syr0ws.bingo.plugin.game.controller;

import com.github.syr0ws.bingo.api.game.Game;
import com.github.syr0ws.bingo.api.game.controller.GameController;
import com.github.syr0ws.bingo.api.game.model.GameModel;
import com.github.syr0ws.bingo.plugin.tool.ListenerManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractController implements GameController {

    private final Plugin plugin;
    private final GameModel model;
    private final ListenerManager manager;
    private boolean loaded;

    public AbstractController(Plugin plugin, GameModel model) {

        if(plugin == null)
            throw new IllegalArgumentException("Plugin cannot be null.");

        if(model == null)
            throw new IllegalArgumentException("GameModel cannot be null.");

        this.plugin = plugin;
        this.model = model;
        this.manager = new ListenerManager(plugin);
    }

    @Override
    public void load() {

        if(this.loaded)
            throw new IllegalStateException("Controller already loaded.");

        this.registerListeners(this.manager);
        this.loaded = true;
    }

    @Override
    public void unload() {

        if(!this.loaded)
            throw new IllegalStateException("Controller not loaded.");

        this.manager.unregisterAll();
        this.loaded = false;
    }

    public void registerListeners(ListenerManager manager) {}

    public Plugin getPlugin() {
        return this.plugin;
    }

    public GameModel getModel() {
        return this.model;
    }
}
