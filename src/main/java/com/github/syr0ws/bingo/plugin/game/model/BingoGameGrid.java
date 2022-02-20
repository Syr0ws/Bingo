package com.github.syr0ws.bingo.plugin.game.model;

import com.github.syr0ws.bingo.api.game.model.GameGrid;
import org.bukkit.Material;

public class BingoGameGrid implements GameGrid {

    private final Material[][] grid;

    public BingoGameGrid(Material[][] grid) {

        if(grid == null)
            throw new IllegalArgumentException("Grid cannot be null.");

        if(grid.length != grid[0].length)
            throw new IllegalArgumentException("Invalid grid. It must be a square array.");

        this.grid = grid;
    }

    @Override
    public Material getItem(int row, int column) {

        int size = this.getSize();

        if(row < 0 || row >= size)
            throw new IllegalArgumentException(String.format("Row cannot must be between 1 and %d.", size - 1));

        if(column < 0 || column >= size)
            throw new IllegalArgumentException(String.format("Column cannot must be between 1 and %d.", size - 1));

        return this.grid[row][column];
    }

    @Override
    public int[] getCoordinates(Material material) {

        if(material == null)
            throw new IllegalArgumentException("Material cannot be null.");

        int size = this.getSize();

        for(int row = 0; row < size; row++) {

            for(int column = 0; column < size; column++) {

                if(this.grid[row][column] == material)
                    return new int[]{row, column};
            }
        }

        throw new NullPointerException("Material not found.");
    }

    @Override
    public boolean contains(Material material) {

        if(material == null)
            throw new IllegalArgumentException("Material cannot be null.");

        int size = this.getSize();

        for(int row = 0; row < size; row++) {

            for(int column = 0; column < size; column++) {

                if(this.grid[row][column] == material)
                    return true;
            }
        }
        return false;
    }

    @Override
    public int getSize() {
        return this.grid.length;
    }
}
