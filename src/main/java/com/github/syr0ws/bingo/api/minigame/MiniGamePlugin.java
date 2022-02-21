package com.github.syr0ws.bingo.api.minigame;

import org.bukkit.plugin.Plugin;

public interface MiniGamePlugin extends Plugin {

    MiniGameModel getModel();

    MiniGameController getController();
}
