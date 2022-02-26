package com.github.syr0ws.bingo.plugin.controller;

import com.github.syr0ws.bingo.api.game.controller.Controller;
import com.github.syr0ws.bingo.api.minigame.MiniGamePlugin;
import com.github.syr0ws.bingo.plugin.tool.ListenerManager;

public abstract class AbstractController implements Controller {

    private final MiniGamePlugin plugin;
    private final ListenerManager manager;
    private boolean loaded;

    public AbstractController(MiniGamePlugin plugin) {

        if(plugin == null)
            throw new IllegalArgumentException("Plugin cannot be null.");

        this.plugin = plugin;
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

    public MiniGamePlugin getPlugin() {
        return this.plugin;
    }

    public boolean isLoaded() {
        return this.loaded;
    }
}
