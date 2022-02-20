package com.github.syr0ws.bingo.plugin.game.model;

import com.github.syr0ws.bingo.api.tool.ChangeType;

public enum BingoGameModelChangeType implements ChangeType {

    ADD_PLAYER("player"), REMOVE_PLAYER("player"), TIME("time"), STATE("state");

    private final String key;

    BingoGameModelChangeType(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return this.key;
    }
}
