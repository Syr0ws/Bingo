package com.github.syr0ws.bingo.plugin.tool;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ListenerManager {

    private final Plugin plugin;
    private final Set<Listener> listeners = new HashSet<>();

    public ListenerManager(Plugin plugin) {

        if(plugin == null)
            throw new IllegalArgumentException("Plugin cannot be null.");

        this.plugin = plugin;
    }

    public void registerListener(Listener listener) {

        boolean added = this.listeners.add(listener);

        if(!added) return;

        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(listener, this.plugin);
    }

    public void unregisterListener(Listener listener) {

        boolean removed = this.listeners.remove(listener);

        if(removed) HandlerList.unregisterAll(listener);
    }

    public void unregisterAll() {
        this.listeners.forEach(HandlerList::unregisterAll);
        this.listeners.clear();
    }

    public Set<Listener> getListeners() {
        return Collections.unmodifiableSet(this.listeners);
    }
}
