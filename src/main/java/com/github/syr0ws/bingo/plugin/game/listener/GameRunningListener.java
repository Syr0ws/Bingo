package com.github.syr0ws.bingo.plugin.game.listener;

import com.github.syr0ws.bingo.api.game.model.GameModel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.CraftItemEvent;

public class GameRunningListener implements Listener {

    private final GameModel model;

    public GameRunningListener(GameModel model) {

        if(model == null)
            throw new IllegalArgumentException("GameModel cannot be null.");

        this.model = model;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerPickupItem(EntityPickupItemEvent event) {

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCraftItem(CraftItemEvent event) {

    }
}
