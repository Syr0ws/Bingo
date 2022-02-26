package com.github.syr0ws.bingo.plugin.game.listener;

import com.github.syr0ws.bingo.api.game.model.GameModel;
import com.github.syr0ws.bingo.api.minigame.MiniGameModel;
import com.github.syr0ws.bingo.api.minigame.MiniGamePlugin;
import com.github.syr0ws.bingo.api.settings.GameSettings;
import com.github.syr0ws.bingo.api.settings.MutableSetting;
import org.bukkit.GameMode;
import org.bukkit.Location;
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
import org.bukkit.event.player.PlayerJoinEvent;

public class GameWaitingListener implements Listener {

    private final MiniGamePlugin plugin;
    private final GameModel model;

    public GameWaitingListener(MiniGamePlugin plugin, GameModel model) {

        if(plugin == null)
            throw new IllegalArgumentException("MiniGamePlugin cannot be null.");

        if(model == null)
            throw new IllegalArgumentException("GameModel cannot be null.");

        this.plugin = plugin;
        this.model = model;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        if(!this.model.hasPlayer(player.getUniqueId())) return;

        player.setGameMode(GameMode.SURVIVAL);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setExp(0);
        player.setLevel(0);
        player.getInventory().clear();

        MiniGameModel miniGameModel = this.plugin.getModel();
        GameSettings settings = miniGameModel.getSettings();

        MutableSetting<Location> setting = settings.getGameSpawnSetting();

        player.teleport(setting.getValue());
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

        if(!(entity instanceof Player)) return;

        boolean exists = this.model.hasPlayer(entity.getUniqueId());

        if(exists) cancellable.setCancelled(true);
    }
}
