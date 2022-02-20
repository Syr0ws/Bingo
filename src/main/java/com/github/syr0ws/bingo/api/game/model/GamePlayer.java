package com.github.syr0ws.bingo.api.game.model;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface GamePlayer {

    String getName();

    UUID getUUID();

    Player getPlayer();

    boolean isOnline();
}
