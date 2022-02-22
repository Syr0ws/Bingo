package com.github.syr0ws.bingo.plugin.tool;

import com.github.syr0ws.bingo.plugin.util.TextUtil;

public enum Text {

    BINGO_INVENTORY_TITLE("&6Bingo"),
    GAME_STARTING_IN("&eDébut dans &6%d&e."),
    GAME_STARTED("&6Début de la partie !");

    private final String text;

    Text(String text) {
        this.text = text;
    }

    public String get() {
        return TextUtil.parseColors(this.text);
    }
}
