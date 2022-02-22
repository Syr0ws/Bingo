package com.github.syr0ws.bingo.plugin.controller;

import com.github.syr0ws.bingo.api.game.controller.Controller;
import com.github.syr0ws.bingo.plugin.tool.ListenerManager;
import org.bukkit.plugin.Plugin;

public abstract class AbstractController implements Controller {

    private final Plugin plugin;
    private final ListenerManager manager;
    private boolean loaded;

    public AbstractController(Plugin plugin) {

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

    public Plugin getPlugin() {
        return this.plugin;
    }
}
