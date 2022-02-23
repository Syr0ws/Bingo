package com.github.syr0ws.bingo.plugin.game.model;

import com.github.syr0ws.bingo.api.game.model.GameGrid;
import com.github.syr0ws.bingo.api.game.model.GameGridGenerator;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DefaultGameGridGenerator implements GameGridGenerator {

    public static final int DEFAULT_GRID_SIZE = 5;

    private static final Random RANDOM = new Random();

    @Override
    public GameGrid generate(int size, List<Material> banList) {

        if(size <= 0)
            throw new IllegalArgumentException("Size must be strictly positive.");

        Material[][] grid = new Material[size][size];
        List<Material> added = new ArrayList<>();

        for(int row = 0; row < size; row++) {

            for(int column = 0; column < size; column++) {

                Material material = this.findMaterial(added, banList);

                grid[row][column] = material;

                added.add(material);
            }
        }
        return new BingoGameGrid(grid);
    }

    private Material findMaterial(List<Material> added, List<Material> banList) {

        Material[] materials = Material.values();
        int length = materials.length;

        Material material = null;

        while(material == null) {

            int index = RANDOM.nextInt(length);
            Material found = materials[index];

            if(!added.contains(found) && !banList.contains(found))
                material = found;
        }
        return material;
    }
}
