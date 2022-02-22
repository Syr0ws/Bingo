package com.github.syr0ws.bingo;

import com.github.syr0ws.bingo.api.minigame.MiniGameController;
import com.github.syr0ws.bingo.api.minigame.MiniGameModel;
import com.github.syr0ws.bingo.api.minigame.MiniGamePlugin;
import com.github.syr0ws.bingo.plugin.commands.CommandBingo;
import com.github.syr0ws.bingo.plugin.commands.CommandBingoTabCompleter;
import com.github.syr0ws.bingo.plugin.minigame.controller.BingoMiniGameController;
import com.github.syr0ws.bingo.plugin.minigame.model.BingoMiniGameModel;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class BingoPlugin extends JavaPlugin implements MiniGamePlugin {

    private MiniGameModel model;
    private MiniGameController controller;

    @Override
    public void onEnable() {
        super.saveDefaultConfig();
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

        PluginCommand command = super.getCommand("bingo");

        Objects.requireNonNull(command); // To suppress the warning.

        command.setExecutor(new CommandBingo(this));
        command.setTabCompleter(new CommandBingoTabCompleter(this.model));
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
