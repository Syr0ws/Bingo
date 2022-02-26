package com.github.syr0ws.bingo.api.game.model;

import java.util.Optional;

public enum GameState {

    WAITING, TELEPORTING, RUNNING, FINISHED;

    public static Optional<GameState> getNext(GameState state) {

        if(state == null)
            throw new IllegalArgumentException("GameState cannot be null.");

        GameState[] states = GameState.values();
        int ordinal = state.ordinal();

        GameState next = ordinal + 1 < states.length ? states[ordinal + 1] : null;

        return Optional.ofNullable(next);
    }
}
