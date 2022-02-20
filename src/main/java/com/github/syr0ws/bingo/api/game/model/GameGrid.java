package com.github.syr0ws.bingo.api.game.model;

import org.bukkit.Material;

public interface GameGrid {

    Material getItem(int row, int column);

    int[] getCoordinates(Material material);

    boolean contains(Material material);

    int getSize();
}
