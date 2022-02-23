package com.github.syr0ws.bingo.api.settings;

public interface Setting<T> {

    T getValue();

    String getName();
}
