package com.github.syr0ws.bingo.api.game.model;

public interface GamePlayerGrid {

    void addFoundItem(int row, int column);

    boolean isItemFound(int row, int column);

    int countFoundItems();

    boolean[][] get();

    int getSize();
}
