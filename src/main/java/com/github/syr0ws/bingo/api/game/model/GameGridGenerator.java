package com.github.syr0ws.bingo.api.game.model;

import org.bukkit.Material;

import java.util.List;

public interface GameGridGenerator {

    GameGrid generate(int size, List<Material> banList);
}
