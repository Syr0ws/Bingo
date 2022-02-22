package com.github.syr0ws.bingo.plugin.message;

import com.github.syr0ws.bingo.api.message.MessageType;

public enum GameMessageType implements MessageType {

    CONTROLLER_DONE,
    GAME_STARTED,
    GAME_FINISHED,
    START_GAME,
    TELEPORTATION_FINISHED,
    ADD_PLAYER,
    REMOVE_PLAYER,
    GAME_TIME_CHANGE,
    GAME_STATE_CHANGE;
}
