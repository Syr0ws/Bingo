package com.github.syr0ws.bingo.plugin.game.listener;

import com.github.syr0ws.bingo.api.game.model.GameModel;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class GameRunningListener implements Listener {

    private final GameModel model;

    public GameRunningListener(GameModel model) {

        if(model == null)
            throw new IllegalArgumentException("GameModel cannot be null.");

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

    private boolean handleItemFound(Player player, ItemStack item) {

        UUID uuid = player.getUniqueId();
        Material material = item.getType();

        if(!this.model.hasPlayer(uuid))
            return false;

        if(!this.model.getGrid().contains(material))
            return false;

        this.model.setFoundItem(uuid, material);

        return true;
    }
}
