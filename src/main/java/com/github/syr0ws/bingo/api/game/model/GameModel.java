package com.github.syr0ws.bingo.api.game.model;

import com.github.syr0ws.bingo.api.message.Observable;
import org.bukkit.Material;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GameModel extends Observable {

    void setFoundItem(UUID uuid, Material material);

    boolean checkWinConditions();

    void addTime();

    int getTime();

    GameGrid getGrid();

    Optional<GamePlayerGrid> getPlayerGrid(UUID uuid);

    void addPlayer(GamePlayer player);

    void removePlayer(GamePlayer player);

    boolean hasPlayer(UUID uuid);

    Optional<GamePlayer> getPlayer(UUID uuid);

    List<GamePlayer> getPlayers();
}
