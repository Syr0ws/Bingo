package com.github.syr0ws.bingo.plugin.tool;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class Task extends BukkitRunnable {

    private final Plugin plugin;
    private boolean running;

    public Task(Plugin plugin) {

        if(plugin == null)
            throw new IllegalArgumentException("Plugin cannot be null.");

        this.plugin = plugin;
    }

    public void start() {

        if(this.running)
            throw new IllegalStateException("Task already running.");

        this.running = true;
    }

    public void stop() {

        if(!this.running)
            throw new IllegalStateException("Task not running.");

        this.running = false;
        this.cancel();
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    public boolean isRunning() {
        return this.running;
    }
}
