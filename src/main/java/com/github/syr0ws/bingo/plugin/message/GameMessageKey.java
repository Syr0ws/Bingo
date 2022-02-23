package com.github.syr0ws.bingo.plugin.message;

import com.github.syr0ws.bingo.api.message.MessageKey;

public enum GameMessageKey implements MessageKey {

    PLAYER("player"),
    CONTROLLER("controller"),
    GAME("game"), TIME("time"),
    GAME_STATE("state"),
    GRID_LINES("lines"),
    PLAYER_GRID("player-grid");

    private final String key;

    GameMessageKey(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return this.key;
    }
}
