package com.github.syr0ws.bingo.api.tool;

public interface ChangeData {

    <T> void set(String key, Class<T> clazz, T object);

    <T> T get(String key, Class<T> clazz);

    boolean has(String key);
}
