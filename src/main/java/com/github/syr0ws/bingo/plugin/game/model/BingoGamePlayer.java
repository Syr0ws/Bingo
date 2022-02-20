package com.github.syr0ws.bingo.plugin.game.model;

import com.github.syr0ws.bingo.api.game.model.GamePlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BingoGamePlayer implements GamePlayer {

    private final String name;
    private final UUID uuid;

    public BingoGamePlayer(Player player) {

        if(player == null)
            throw new IllegalArgumentException("Player cannot be null.");

        this.name = player.getName();
        this.uuid = player.getUniqueId();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public UUID getUUID() {
        return this.uuid;
    }

    @Override
    public Player getPlayer() {
        return Bukkit.getPlayer(this.uuid);
    }

    @Override
    public boolean isOnline() {
        return this.getPlayer() != null;
    }
}
