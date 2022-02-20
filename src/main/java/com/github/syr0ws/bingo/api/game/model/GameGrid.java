package com.github.syr0ws.bingo.api.game.model;

import org.bukkit.Material;

public interface GameGrid {

    Material getItem(int row, int column);

    Material[][] get();

    int getSize();
}
