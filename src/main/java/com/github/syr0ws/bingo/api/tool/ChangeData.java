package com.github.syr0ws.bingo.api.tool;

public interface ChangeData {

    byte getByte(String key);

    short getShort(String key);

    int getInt(String key);

    long getLong(String key);

    float getFloat(String key);

    double getDouble(String key);

    boolean getBoolean(String key);

    String getString(String key);

    <T> T getObject(String key, Class<T> clazz);
}
