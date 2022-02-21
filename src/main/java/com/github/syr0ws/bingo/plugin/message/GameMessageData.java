package com.github.syr0ws.bingo.plugin.message;

import com.github.syr0ws.bingo.api.message.MessageData;

import java.util.HashMap;
import java.util.Map;

public class GameMessageData implements MessageData {

    private final Map<String, Data<?>> objects = new HashMap<>();

    @Override
    public <T> void set(String key, Class<T> clazz, T object) {

        if(key == null)
            throw new IllegalArgumentException("Key cannot be null.");

        if(clazz == null)
            throw new IllegalArgumentException("Class cannot be null.");

        if(object == null)
            throw new IllegalArgumentException("Object cannot be null.");

        Data<T> data = new Data<>(clazz, object);

        this.objects.put(key, data);
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {

        if(key == null)
            throw new IllegalArgumentException("Key cannot be null.");

        if(clazz == null)
            throw new IllegalArgumentException("Class cannot be null.");

        Data<?> data = this.objects.getOrDefault(key, null);

        if(data == null)
            throw new NullPointerException(String.format("Key '%s' doesn't exist.", key));

        if(!clazz.equals(data.clazz))
            throw new RuntimeException(String.format("Incompatible types : %s and %s.", clazz.getSimpleName(), data.clazz.getSimpleName()));

        @SuppressWarnings("unchecked")
        Data<T> casted = (Data<T>) data;

        return casted.object;
    }

    @Override
    public boolean has(String key) {
        return this.objects.containsKey(key);
    }

    private record Data<T>(Class<T> clazz, T object) {}
}
