package com.github.syr0ws.bingo.api.game.model;

import com.github.syr0ws.bingo.api.message.Observable;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.*;

public interface GameModel extends Observable {

    boolean setFoundItem(UUID uuid, Material material);

    boolean hasWin(UUID uuid);

    boolean checkWinConditions();

    GameState getState();

    void setState(GameState state);

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

    Map<GamePlayer, Integer> getPlayersWithMostFoundItems();

    List<Player> getOnlinePlayers();
}
