package com.github.syr0ws.bingo.plugin.tool;

import com.github.syr0ws.bingo.plugin.util.TextUtil;

public enum Text {

    BINGO_INVENTORY_TITLE("&6Bingo");

    private final String text;

    Text(String text) {
        this.text = text;
    }

    public String get() {
        return TextUtil.parseColors(this.text);
    }
}
