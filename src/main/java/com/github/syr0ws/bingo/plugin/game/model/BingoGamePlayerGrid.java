package com.github.syr0ws.bingo.plugin.game.model;

import com.github.syr0ws.bingo.api.game.model.GamePlayerGrid;
import com.github.syr0ws.bingo.api.game.model.GridLine;

import java.util.HashSet;
import java.util.Set;

public class BingoGamePlayerGrid implements GamePlayerGrid {

    private final boolean[][] grid;
    private int completedLines;

    public BingoGamePlayerGrid(int size) {

        if(size <= 0)
            throw new IllegalArgumentException("Size mist be strictly positive.");

        this.grid = new boolean[size][size];
    }

    @Override
    public Set<GridLine> addFoundItem(int row, int column) {

        int size = this.getSize();

        if(row < 0 || row >= size)
            throw new IllegalArgumentException(String.format("Row must be between 0 and %d.", size));

        if(column < 0 || column >= size)
            throw new IllegalArgumentException(String.format("Column must be between 0 and %d.", size));

        this.grid[row][column] = true;

        Set<GridLine> lines = this.getCompletedLines(row, column);

        this.completedLines += lines.size();

        return lines;
    }

    @Override
    public boolean isItemFound(int row, int column) {

        int size = this.getSize();

        if(row < 0 || row >= size)
            throw new IllegalArgumentException(String.format("Row must be between 0 and %d.", size));

        if(column < 0 || column >= size)
            throw new IllegalArgumentException(String.format("Column must be between 0 and %d.", size));

        return this.grid[row][column];
    }

    @Override
    public int countCompletedLines() {
        return this.completedLines;
    }

    @Override
    public int countFoundItems() {

        int size = this.getSize();
        int accumulator = 0;

        for(int row = 0; row < size; row++) {

            for(int column = 0; column < size; column++) {

                if(this.grid[row][column]) accumulator += 1;
            }
        }
        return accumulator;
    }

    @Override
    public int countItems() {
        int size = this.getSize();
        return size * size;
    }

    @Override
    public int getSize() {
        return this.grid[0].length;
    }

    private Set<GridLine> getCompletedLines(int row, int column) {

        Set<GridLine> lines = new HashSet<>();

        if(this.isRowComplete(row))
            lines.add(GridLine.ROW);

        if(this.isColumnComplete(column))
            lines.add(GridLine.COLUMN);

        if(this.isLeftDiagonalComplete(row, column) || this.isRightDiagonalComplete(row, column))
            lines.add(GridLine.DIAGONAL);

        return lines;
    }

    private boolean isRowComplete(int row) {

        for(int i = 0; i < this.getSize(); i++) {

            if(!this.grid[row][i]) return false;
        }
        return true;
    }

    private boolean isColumnComplete(int column) {

        for(int i = 0; i < this.getSize(); i++) {

            if(!this.grid[i][column]) return false;
        }
        return true;
    }

    private boolean isLeftDiagonalComplete(int row, int column) {

        if(row != column) return false;

        int size = this.getSize();

        for(int i = 0; i < size; i++) {

            if(!this.grid[i][i]) return false;
        }
        return true;
    }

    private boolean isRightDiagonalComplete(int row, int column) {

        int size = this.getSize();

        // Coordinates not in right diagonal.
        if(row + column != size - 1)
            return false;

        for(int i = 0; i < size; i++) {

            for(int j = 0; j < size; j++) {

                if(i + j == size - 1 && !this.grid[i][j]) return false;
            }
        }
        return true;
    }
}
