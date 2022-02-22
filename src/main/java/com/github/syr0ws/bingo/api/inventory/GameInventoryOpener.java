package com.github.syr0ws.bingo.api.inventory;

import com.github.syr0ws.bingo.api.game.model.GameModel;
import org.bukkit.entity.Player;

public interface GameInventoryOpener {

    void open(Player player, GameModel model);
}
