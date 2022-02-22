package com.github.syr0ws.bingo.plugin.inventory;

import com.github.syr0ws.bingo.api.game.model.GameGrid;
import com.github.syr0ws.bingo.api.game.model.GameModel;
import com.github.syr0ws.bingo.api.game.model.GamePlayerGrid;
import com.github.syr0ws.bingo.api.inventory.GameInventoryOpener;
import com.github.syr0ws.bingo.plugin.tool.Text;
import fr.minuskube.inv.InventoryManager;
import fr.minuskube.inv.SmartInventory;
import org.bukkit.entity.Player;

import java.util.Optional;

public class BingoInventoryOpener implements GameInventoryOpener {

    private final InventoryManager manager;

    public BingoInventoryOpener(InventoryManager manager) {

        if(manager == null)
            throw new IllegalArgumentException("InventoryManager cannot be null.");

        this.manager = manager;
    }

    @Override
    public void open(Player player, GameModel model) {

        Optional<GamePlayerGrid> optional = model.getPlayerGrid(player.getUniqueId());

        if(optional.isEmpty())
            throw new NullPointerException("GamePlayer not found.");

        GamePlayerGrid playerGrid = optional.get();
        GameGrid gameGrid = model.getGrid();

        SmartInventory inventory = this.getInventory(gameGrid, playerGrid);
        inventory.open(player);
    }

    private SmartInventory getInventory(GameGrid gameGrid, GamePlayerGrid playerGrid) {
        return SmartInventory.builder()
                .title(Text.BINGO_INVENTORY_TITLE.get())
                .size(5, 9)
                .manager(this.manager)
                .provider(new BingoInventoryProvider(gameGrid, playerGrid))
                .build();
    }
}
