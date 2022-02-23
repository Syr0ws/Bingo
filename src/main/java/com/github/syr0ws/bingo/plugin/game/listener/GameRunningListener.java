package com.github.syr0ws.bingo.plugin.game.listener;

import com.github.syr0ws.bingo.api.game.model.GameModel;
import com.github.syr0ws.bingo.api.minigame.MiniGameModel;
import com.github.syr0ws.bingo.api.minigame.MiniGamePlugin;
import com.github.syr0ws.bingo.api.settings.GameSettings;
import com.github.syr0ws.bingo.api.settings.MutableSetting;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class GameRunningListener implements Listener {

    private final MiniGamePlugin plugin;
    private final GameModel model;

    public GameRunningListener(MiniGamePlugin plugin, GameModel model) {

        if(plugin == null)
            throw new IllegalArgumentException("MiniGamePlugin cannot be null.");

        if(model == null)
            throw new IllegalArgumentException("GameModel cannot be null.");

        this.plugin = plugin;
        this.model = model;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerPickupItem(EntityPickupItemEvent event) {

        Entity entity = event.getEntity();
        Item item = event.getItem();
        ItemStack stack = item.getItemStack();

        if(!(entity instanceof Player player)) return;

        boolean handled = this.handleItemFound(player, stack);

        if(!handled) return;

        if(stack.getAmount() > 1) {

            stack.setAmount(stack.getAmount() - 1);
            item.setItemStack(stack);

        } else {

            item.remove();
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCraftItem(CraftItemEvent event) {

        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        if(item == null) return;

        boolean handled = this.handleItemFound(player, item);

        if(!handled) return;

        if(item.getAmount() > 1) {

            item.setAmount(item.getAmount() - 1);
            event.setCurrentItem(item);

        } else event.setCurrentItem(null);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDamage(EntityDamageEvent event) {

        Entity entity = event.getEntity();

        if(!(entity instanceof Player player)) return;

        MiniGameModel miniGameModel = this.plugin.getModel();

        GameSettings settings = miniGameModel.getSettings();
        MutableSetting<Integer> setting = settings.getInvincibilityDurationSetting();

        if(this.model.getTime() > setting.getValue()) return;

        if(this.model.hasPlayer(player.getUniqueId())) event.setCancelled(true);
    }

    private boolean handleItemFound(Player player, ItemStack item) {

        UUID uuid = player.getUniqueId();
        Material material = item.getType();

        if(!this.model.hasPlayer(uuid))
            return false;

        if(!this.model.getGrid().contains(material))
            return false;

        return this.model.setFoundItem(uuid, material);
    }
}
