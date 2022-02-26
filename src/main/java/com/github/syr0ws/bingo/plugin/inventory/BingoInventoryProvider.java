package com.github.syr0ws.bingo.plugin.inventory;

import com.github.syr0ws.bingo.api.game.model.GameGrid;
import com.github.syr0ws.bingo.api.game.model.GamePlayerGrid;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BingoInventoryProvider implements InventoryProvider {

    private final GameGrid gameGrid;
    private final GamePlayerGrid playerGrid;

    public BingoInventoryProvider(GameGrid gameGrid, GamePlayerGrid playerGrid) {

        if(gameGrid == null)
            throw new IllegalArgumentException("GameGrid cannot be null.");

        if(playerGrid == null)
            throw new IllegalArgumentException("PlayerGrid cannot be null.");

        this.gameGrid = gameGrid;
        this.playerGrid = playerGrid;
    }

    @Override
    public void init(Player player, InventoryContents contents) {

        int size = this.gameGrid.getSize();

        // Adding all the items of the grid into the inventory.
        for(int row = 0; row < size; row++) {

            for(int column = 0; column < size; column++) {

                Material material = this.gameGrid.getItem(row, column);
                ItemStack item = new ItemStack(material);

                // If the current item has been found by the player, enchanting it.
                if(this.playerGrid.isItemFound(row, column)) {

                    ItemMeta meta = item.getItemMeta();
                    meta.addEnchant(Enchantment.DURABILITY, 1, false);
                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    item.setItemMeta(meta);
                }

                ClickableItem clickable = ClickableItem.empty(item);

                contents.set(row, column + 2, clickable);
            }
        }
    }

    @Override
    public void update(Player player, InventoryContents contents) {}
}
