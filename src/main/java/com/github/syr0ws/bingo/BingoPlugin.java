package com.github.syr0ws.bingo;

import com.github.syr0ws.bingo.api.minigame.MiniGameController;
import com.github.syr0ws.bingo.api.minigame.MiniGameModel;
import com.github.syr0ws.bingo.api.minigame.MiniGamePlugin;
import org.bukkit.plugin.java.JavaPlugin;

public class BingoPlugin extends JavaPlugin implements MiniGamePlugin {

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public MiniGameModel getModel() {
        return null;
    }

    @Override
    public MiniGameController getController() {
        return null;
    }
}
