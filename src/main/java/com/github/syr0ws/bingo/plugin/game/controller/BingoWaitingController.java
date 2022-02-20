package com.github.syr0ws.bingo.plugin.game.controller;

import com.github.syr0ws.bingo.api.game.controller.GameController;
import com.github.syr0ws.bingo.api.game.model.GameModel;
import com.github.syr0ws.bingo.api.game.model.GamePlayer;
import com.github.syr0ws.bingo.api.game.model.GameState;
import com.github.syr0ws.bingo.api.tool.Change;
import com.github.syr0ws.bingo.plugin.game.model.BingoGamePlayer;
import com.github.syr0ws.bingo.plugin.tool.ListenerManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class BingoWaitingController extends AbstractController implements GameController {

    public BingoWaitingController(Plugin plugin, GameModel model) {
        super(plugin, model);
    }

    @Override
    public void load() {
        super.load();
    }

    @Override
    public void unload() {
        super.unload();
    }

    @Override
    public void registerListeners(ListenerManager manager) {
        manager.registerListener(new ControllerListener());
    }

    @Override
    public GameState getState() {
        return GameState.WAITING;
    }

    @Override
    public void onChange(Change change) {

    }

    private class ControllerListener implements Listener {

        @EventHandler
        public void onPlayerJoin(GamePlayerJoinEvent event) {

            GamePlayer player = event.getPlayer();
            GameModel model = getModel();

            model.addPlayer(player);
        }

        @EventHandler
        public void onPlayerQuit(GamePlayerQuitEvent event) {

            GamePlayer player = event.getPlayer();
            GameModel model = getModel();

            model.removePlayer(player);
        }

        @EventHandler(priority = EventPriority.HIGHEST)
        public void onBlockBreak(BlockBreakEvent event) {
            this.cancelIfExists(event, event.getPlayer());
        }

        @EventHandler(priority = EventPriority.HIGHEST)
        public void onBlockPlace(BlockPlaceEvent event) {
            this.cancelIfExists(event, event.getPlayer());
        }

        @EventHandler(priority = EventPriority.HIGHEST)
        public void onInventoryClick(InventoryClickEvent event) {
            this.cancelIfExists(event, event.getWhoClicked());
        }

        @EventHandler(priority = EventPriority.HIGHEST)
        public void onPlayerDamage(EntityDamageEvent event) {
            this.cancelIfExists(event, event.getEntity());
        }

        @EventHandler(priority = EventPriority.HIGHEST)
        public void onPlayerFoodLevelChange(FoodLevelChangeEvent event) {
            this.cancelIfExists(event, event.getEntity());
        }

        @EventHandler(priority = EventPriority.HIGHEST)
        public void onPlayerDropItem(EntityDropItemEvent event) {
            this.cancelIfExists(event, event.getEntity());
        }

        @EventHandler(priority = EventPriority.HIGHEST)
        public void onPlayerPickupItem(EntityPickupItemEvent event) {
            this.cancelIfExists(event, event.getEntity());
        }

        private void cancelIfExists(Cancellable cancellable, Entity entity) {

            GameModel model = getModel();

            if(!(entity instanceof Player)) return;

            boolean exists = model.hasPlayer(entity.getUniqueId());

            if(exists) cancellable.setCancelled(true);
        }
    }
}
