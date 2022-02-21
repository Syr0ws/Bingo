package com.github.syr0ws.bingo.plugin.message;

import com.github.syr0ws.bingo.api.message.Message;
import com.github.syr0ws.bingo.api.message.MessageData;
import com.github.syr0ws.bingo.api.message.MessageType;

public class GameMessage implements Message {

    private final MessageType type;
    private final MessageData data;

    public GameMessage(MessageType type) {

        if(type == null)
            throw new IllegalArgumentException("ChangeType cannot be null.");

        this.type = type;
        this.data = new GameMessageData();
    }

    @Override
    public MessageType getType() {
        return this.type;
    }

    @Override
    public MessageData getData() {
        return this.data;
    }
}
