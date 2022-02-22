package com.github.syr0ws.bingo.plugin.game.model;

import com.github.syr0ws.bingo.api.game.model.*;
import com.github.syr0ws.bingo.plugin.message.GameMessageKey;
import com.github.syr0ws.bingo.plugin.message.GameMessageType;
import com.github.syr0ws.bingo.plugin.message.GameMessageUtil;
import com.github.syr0ws.bingo.plugin.tool.AbstractObservable;
import org.bukkit.Material;

import java.util.*;

public class BingoGameModel extends AbstractObservable implements GameModel {

    private int time;
    private boolean starting;

    private final GameGrid grid;

    // Using maps for better performances when retrieving data.
    private final Map<UUID, GamePlayer> players = new HashMap<>();
    private final Map<UUID, GamePlayerGrid> grids = new HashMap<>();

    public BingoGameModel(GameGrid grid) {

        // TODO To fix.
        // if(grid == null)
        // throw new IllegalStateException("GameGrid cannot be null.");

        this.grid = grid;
        this.time = 0;
    }

    @Override
    public void setFoundItem(UUID uuid, Material material) {

        if(uuid == null)
            throw new IllegalArgumentException("UUID cannot be null.");

        if(material == null)
            throw new IllegalArgumentException("Material cannot be null.");

        if(!this.hasPlayer(uuid))
            throw new NullPointerException("GamePlayer not found.");

        if(!this.grid.contains(material))
            throw new IllegalArgumentException("Material doesn't belong to GameGrid.");

        int[] coordinates = this.grid.getCoordinates(material);
        int row = coordinates[0], column = coordinates[1];

        GamePlayerGrid grid = this.grids.get(uuid);
        grid.addFoundItem(row, column); // TODO Handle data.
    }

    @Override
    public boolean checkWinConditions() {
        return false;
    }

    @Override
    public void setStarting(boolean starting) {
        this.starting = starting;
    }

    @Override
    public boolean isStarting() {
        return this.starting;
    }

    @Override
    public void addTime() {

        this.time++;

        GameMessageUtil.sendSimpleMessage(this, GameMessageType.GAME_TIME_CHANGE, GameMessageKey.TIME, Integer.class, this.time);
    }

    @Override
    public int getTime() {
        return this.time;
    }

    @Override
    public GameGrid getGrid() {
        return this.grid;
    }

    @Override
    public Optional<GamePlayerGrid> getPlayerGrid(UUID uuid) {
        GamePlayerGrid grid = this.grids.getOrDefault(uuid, null);
        return Optional.ofNullable(grid);
    }

    @Override
    public void addPlayer(GamePlayer player) {

        if(player == null)
            throw new IllegalStateException("GamePlayer cannot be null.");

        UUID uuid = player.getUUID();

        if(this.hasPlayer(uuid))
            throw new IllegalArgumentException("Player must be added once.");

        GamePlayerGrid grid = null; // TODO To change.

        this.players.put(uuid, player);
        this.grids.put(uuid, grid);

        GameMessageUtil.sendSimpleMessage(this, GameMessageType.ADD_PLAYER, GameMessageKey.PLAYER, GamePlayer.class, player);
    }

    @Override
    public void removePlayer(GamePlayer player) {

        if(player == null)
            throw new IllegalStateException("GamePlayer cannot be null.");

        UUID uuid = player.getUUID();

        if(!this.hasPlayer(uuid))
            throw new IllegalArgumentException("Player doesn't exist.");

        this.players.remove(uuid);
        this.grids.remove(uuid);

        GameMessageUtil.sendSimpleMessage(this, GameMessageType.REMOVE_PLAYER, GameMessageKey.PLAYER, GamePlayer.class, player);
    }

    @Override
    public boolean hasPlayer(UUID uuid) {
        return this.players.containsKey(uuid);
    }

    @Override
    public Optional<GamePlayer> getPlayer(UUID uuid) {
        GamePlayer player = this.players.getOrDefault(uuid, null);
        return Optional.ofNullable(player);
    }

    @Override
    public List<GamePlayer> getPlayers() {
        return new ArrayList<>(this.players.values());
    }
}
