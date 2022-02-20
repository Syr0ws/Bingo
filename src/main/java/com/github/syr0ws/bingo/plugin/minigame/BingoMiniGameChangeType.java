package com.github.syr0ws.bingo.plugin.minigame;

import com.github.syr0ws.bingo.api.tool.ChangeType;

public enum BingoMiniGameChangeType implements ChangeType {

    GAME_STARTED("game"), GAME_FINISHED("game");

    private final String key;

    BingoMiniGameChangeType(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return this.key;
    }
}
