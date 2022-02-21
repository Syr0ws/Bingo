package com.github.syr0ws.bingo.plugin.tool;

public enum Permission {

    COMMAND_BINGO("command.bingo"),
    COMMAND_BINGO_START("command.bingo.start"),
    COMMAND_BINGO_STOP("command.bingo.stop");

    private final String permission;

    Permission(String permission) {
        this.permission = "bingo." + permission;
    }

    public String getPermission() {
        return this.permission;
    }
}
