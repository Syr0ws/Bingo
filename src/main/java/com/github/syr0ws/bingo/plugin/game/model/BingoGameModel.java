package com.github.syr0ws.bingo.plugin.game.model;

import com.github.syr0ws.bingo.api.game.model.*;
import com.github.syr0ws.bingo.api.message.Message;
import com.github.syr0ws.bingo.api.message.MessageData;
import com.github.syr0ws.bingo.api.settings.GameSettings;
import com.github.syr0ws.bingo.api.settings.MutableSetting;
import com.github.syr0ws.bingo.plugin.message.GameMessage;
import com.github.syr0ws.bingo.plugin.message.GameMessageKey;
import com.github.syr0ws.bingo.plugin.message.GameMessageType;
import com.github.syr0ws.bingo.plugin.message.GameMessageUtil;
import com.github.syr0ws.bingo.plugin.tool.AbstractObservable;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class BingoGameModel extends AbstractObservable implements GameModel {

    private int time;
    private boolean starting;
    private GameState state;

    private final GameGrid grid;
    private final GameSettings settings;
    private final Map<UUID, PlayerData> data = new HashMap<>();

    public BingoGameModel(GameGrid grid, GameSettings settings) {

        if(grid == null)
          throw new IllegalStateException("GameGrid cannot be null.");

        if(settings == null)
            throw new IllegalArgumentException("GameSettings cannot be null.");

        this.grid = grid;
        this.settings = settings;

        this.state = GameState.WAITING;
        this.time = 0;
    }

    @Override
    public boolean setFoundItem(UUID uuid, Material material) {

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

        PlayerData data = this.data.get(uuid);

        GamePlayer gamePlayer = data.player();
        GamePlayerGrid grid = data.grid();

        // Checking if the item has been already found.
        if(grid.isItemFound(row, column)) return false;

        Set<GridLine> lines = grid.addFoundItem(row, column);

        // Sending ITEM_FOUND message.
        Message messageItemFound = new GameMessage(GameMessageType.ITEM_FOUND);
        MessageData messageItemFoundData = messageItemFound.getData();

        messageItemFoundData.set(GameMessageKey.PLAYER.getKey(), GamePlayer.class, gamePlayer);
        messageItemFoundData.set(GameMessageKey.GRID_LINES.getKey(), Set.class, lines);
        messageItemFoundData.set(GameMessageKey.PLAYER_GRID.getKey(), GamePlayerGrid.class, grid);

        this.sendAll(messageItemFound);

        if(this.hasWin(uuid)) {

            // Sending WIN message.
            Message messageWin = new GameMessage(GameMessageType.WIN);
            MessageData messageWinData = messageWin.getData();

            messageWinData.set(GameMessageKey.PLAYER.getKey(), GamePlayer.class, gamePlayer);

            this.sendAll(messageWin);
        }
        return true;
    }

    @Override
    public boolean hasWin(UUID uuid) {

        if(!this.hasPlayer(uuid))
            throw new NullPointerException("GamePlayer not found.");

        // Player has win if he completed all the required lines.
        MutableSetting<Integer> setting = this.settings.getLinesToCompleteSetting();

        PlayerData data = this.data.get(uuid);
        GamePlayerGrid grid = data.grid();

        return setting.getValue() == grid.countCompletedLines();
    }

    @Override
    public boolean checkWinConditions() {
        return this.data.keySet().stream().anyMatch(this::hasWin);
    }

    @Override
    public GameState getState() {
        return this.state;
    }

    @Override
    public void setState(GameState state) {

        if(state == null)
            throw new IllegalArgumentException("GameState cannot be null.");

        if(state == this.state)
            return;

        if(state.ordinal() < this.state.ordinal())
            throw new IllegalArgumentException("Cannot set previous state.");

        this.state = state;

        GameMessageUtil.sendSimpleMessage(this, GameMessageType.GAME_STATE_CHANGE, GameMessageKey.GAME_STATE, GameState.class, this.state);
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
        PlayerData data = this.data.getOrDefault(uuid, null);
        return data == null ? Optional.empty() : Optional.of(data.grid());
    }

    @Override
    public void addPlayer(GamePlayer player) {

        if(player == null)
            throw new IllegalStateException("GamePlayer cannot be null.");

        UUID uuid = player.getUUID();

        if(this.hasPlayer(uuid))
            throw new IllegalArgumentException("Player must be added once.");

        GamePlayerGrid grid = new BingoGamePlayerGrid(this.grid.getSize());

        PlayerData data = new PlayerData(player, grid);

        this.data.put(uuid, data);

        GameMessageUtil.sendSimpleMessage(this, GameMessageType.ADD_PLAYER, GameMessageKey.PLAYER, GamePlayer.class, player);
    }

    @Override
    public void removePlayer(GamePlayer player) {

        if(player == null)
            throw new IllegalStateException("GamePlayer cannot be null.");

        UUID uuid = player.getUUID();

        if(!this.hasPlayer(uuid))
            throw new IllegalArgumentException("Player doesn't exist.");

        this.data.remove(uuid);

        GameMessageUtil.sendSimpleMessage(this, GameMessageType.REMOVE_PLAYER, GameMessageKey.PLAYER, GamePlayer.class, player);
    }

    @Override
    public boolean hasPlayer(UUID uuid) {
        return this.data.containsKey(uuid);
    }

    @Override
    public Optional<GamePlayer> getPlayer(UUID uuid) {
        PlayerData data = this.data.get(uuid);
        return data == null ? Optional.empty() : Optional.of(data.player());
    }

    @Override
    public List<GamePlayer> getPlayers() {
        return this.data.values().stream()
                .map(PlayerData::player)
                .collect(Collectors.toList());
    }

    @Override
    public Map<GamePlayer, Integer> getPlayersWithMostFoundItems() {

        Map<GamePlayer, Integer> map = new HashMap<>();

        // Retrieving max.
        Optional<Integer> optional = this.data.values().stream()
                .map(data -> data.grid().countFoundItems())
                .max(Integer::compare);

        // Max not found. Should not happen.
        if(optional.isEmpty())
            return map;

        int max = optional.get();

        // Retrieving players with number of items found equals to max.
        this.data.entrySet().stream()
                .filter(data -> data.getValue().grid().countItems() == max)
                .forEach(data -> map.put(data.getValue().player(), max));

        return map;
    }

    @Override
    public List<Player> getOnlinePlayers() {
        return this.data.values().stream()
                .map(PlayerData::player)
                .filter(GamePlayer::isOnline)
                .map(GamePlayer::getPlayer)
                .collect(Collectors.toList());
    }

    private record PlayerData(GamePlayer player, GamePlayerGrid grid) {}
}
