package com.github.syr0ws.bingo.plugin.tool;

import com.github.syr0ws.bingo.plugin.util.TextUtil;

public enum Text {

    BINGO_INVENTORY_TITLE("&6Bingo"),
    GAME_STARTING_IN("&eGame starting in &6%d&e."),
    GAME_STARTED("&6Starting the game !"),
    ITEM_FOUND("&6%s &ehas found an item !"),
    ROW_COMPLETE("&6%s &ehas completed a line !"),
    COLUMN_COMPLETE("&6%s &ehas completed a column !"),
    DIAGONAL_COMPLETE("&6%s &ehas completed a diagonal !"),
    PLAYER_BINGO_COMPLETE("&6%s &ehas completed his bingo grid !"),
    PLAYER_WIN("&6%s &ewins the game !"),
    GAME_TIME_LEFT_MINUTE("&eThere are &6%d minute%s &eleft to complete your bingo."),
    GAME_TIME_LEFT_SECOND("&eThere are &6%d second%s &eleft to complete your bingo."),
    RESTART("&cThe server is restarting. Please reconnect."),
    GAME_FINISHED("&cThe game is finished !");

    private final String text;

    Text(String text) {
        this.text = text;
    }

    public String get() {
        return TextUtil.parseColors(this.text);
    }
}
