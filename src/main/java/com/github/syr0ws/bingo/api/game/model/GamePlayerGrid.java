package com.github.syr0ws.bingo.api.game.model;

import java.util.Set;

public interface GamePlayerGrid {

    Set<GridLine> addFoundItem(int row, int column);

    boolean isItemFound(int row, int column);

    int countFoundItems();

    int getSize();
}
