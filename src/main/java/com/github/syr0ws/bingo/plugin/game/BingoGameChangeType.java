package com.github.syr0ws.bingo.plugin.game;

import com.github.syr0ws.bingo.api.tool.ChangeType;

public enum BingoGameChangeType implements ChangeType {

    DONE("done");

    private final String key;

    BingoGameChangeType(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return this.key;
    }
}
