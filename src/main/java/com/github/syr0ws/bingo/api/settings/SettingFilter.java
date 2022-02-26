package com.github.syr0ws.bingo.api.settings;

@FunctionalInterface
public interface SettingFilter<T> {

    boolean filter(T value);
}
