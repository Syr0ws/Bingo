package com.github.syr0ws.bingo.plugin.events;

import com.github.syr0ws.bingo.api.game.model.GamePlayer;
import org.bukkit.event.Event;

public abstract class GamePlayerEvent extends Event {

    private final GamePlayer player;

    public GamePlayerEvent(GamePlayer player) {

        if(player == null)
            throw new IllegalArgumentException("GamePlayer cannot be null.");

        this.player = player;
    }

    public GamePlayer getPlayer() {
        return this.player;
    }
}
