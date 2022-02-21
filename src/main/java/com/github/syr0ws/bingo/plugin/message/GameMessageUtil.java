package com.github.syr0ws.bingo.plugin.message;

import com.github.syr0ws.bingo.api.message.Message;
import com.github.syr0ws.bingo.api.message.MessageData;
import com.github.syr0ws.bingo.api.message.Observable;

public class GameMessageUtil {

    public static <T> void sendSimpleMessage(Observable observable, GameMessageType type, GameMessageKey key, Class<T> clazz, T object) {

        Message message = new GameMessage(type);

        MessageData data = message.getData();
        data.set(key.getKey(), clazz, object);

        observable.sendAll(message);
    }
}
