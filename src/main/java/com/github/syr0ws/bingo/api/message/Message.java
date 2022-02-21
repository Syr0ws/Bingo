package com.github.syr0ws.bingo.api.message;

public interface Message {

    MessageType getType();

    MessageData getData();
}
