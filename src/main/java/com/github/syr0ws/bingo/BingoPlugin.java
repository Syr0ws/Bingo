package com.github.syr0ws.bingo;

import com.github.syr0ws.bingo.api.minigame.MiniGameController;
import com.github.syr0ws.bingo.api.minigame.MiniGameModel;
import com.github.syr0ws.bingo.api.minigame.MiniGamePlugin;
import com.github.syr0ws.bingo.plugin.commands.CommandBingo;
import com.github.syr0ws.bingo.plugin.minigame.controller.BingoMiniGameController;
import com.github.syr0ws.bingo.plugin.minigame.model.BingoMiniGameModel;
import org.bukkit.plugin.java.JavaPlugin;

public class BingoPlugin extends JavaPlugin implements MiniGamePlugin {

    private MiniGameModel model;
    private MiniGameController controller;

    @Override
    public void onEnable() {
        this.setupModel();
        this.setupController();
        this.registerCommands();
    }

    @Override
    public void onDisable() {
        this.controller.unload();
        this.controller = null;
        this.model = null;
    }

    private void registerCommands() {
        super.getCommand("bingo").setExecutor(new CommandBingo(this));
    }

    private void setupModel() {
        this.model = new BingoMiniGameModel();
    }

    private void setupController() {
        this.controller = new BingoMiniGameController(this, this.model);
        this.controller.load();
    }

    @Override
    public MiniGameModel getModel() {
        return this.model;
    }

    @Override
    public MiniGameController getController() {
        return controller;
    }
}
