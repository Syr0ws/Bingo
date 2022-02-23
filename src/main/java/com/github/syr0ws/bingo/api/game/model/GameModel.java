package com.github.syr0ws.bingo.api.game.model;

import com.github.syr0ws.bingo.api.message.Observable;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GameModel extends Observable {

    boolean setFoundItem(UUID uuid, Material material);

    boolean hasWin(UUID uuid);

    boolean checkWinConditions();

    void setStarting(boolean starting);

    boolean isStarting();

    void addTime();

    int getTime();

    GameGrid getGrid();

    Optional<GamePlayerGrid> getPlayerGrid(UUID uuid);

    void addPlayer(GamePlayer player);

    void removePlayer(GamePlayer player);

    boolean hasPlayer(UUID uuid);

    Optional<GamePlayer> getPlayer(UUID uuid);

    List<GamePlayer> getPlayers();

    List<GamePlayer> getPlayersWithMostFoundItems();

    List<Player> getOnlinePlayers();
}
