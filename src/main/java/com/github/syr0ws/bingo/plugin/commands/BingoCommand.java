package com.github.syr0ws.bingo.plugin.commands;

import com.github.syr0ws.bingo.api.game.Game;
import com.github.syr0ws.bingo.api.minigame.MiniGameModel;
import com.github.syr0ws.bingo.api.minigame.MiniGamePlugin;
import com.github.syr0ws.bingo.plugin.tool.Permission;
import com.github.syr0ws.bingo.plugin.util.TextUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.Objects;
import java.util.Optional;

public class BingoCommand implements CommandExecutor {

    private final MiniGamePlugin plugin;

    public BingoCommand(MiniGamePlugin plugin) {

        if(plugin == null)
            throw new IllegalArgumentException("GamePlugin cannot be null.");

        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        ConfigurationSection section = this.getCommandSection();

        if(!sender.hasPermission(Permission.COMMAND_BINGO.getPermission())) {
            TextUtil.sendMessage(sender, section.getString("no-permission", ""));
            return true;
        }

        if(args.length == 0) {
            this.sendUsages(sender);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "start" -> this.onStartCommand(sender, args);
            case "stop" -> this.onStopCommand(sender, args);
            default -> this.sendUsages(sender);
        }
        return true;
    }

    private void sendUsages(CommandSender sender) {

    }

    private void onStartCommand(CommandSender sender, String[] args) {

        ConfigurationSection section = this.getCommandSection();

        if(!sender.hasPermission(Permission.COMMAND_BINGO_START.getPermission())) {
            TextUtil.sendMessage(sender, section.getString("no-permission", ""));
            return;
        }

        ConfigurationSection startSection = section.getConfigurationSection("start");

        MiniGameModel model = this.plugin.getModel();
        Optional<Game> optional = model.getGame(sender.get)
    }

    private void onStopCommand(CommandSender sender, String[] args) {

        ConfigurationSection section = this.getCommandSection();

        if(!sender.hasPermission(Permission.COMMAND_BINGO_STOP.getPermission())) {
            TextUtil.sendMessage(sender, section.getString("no-permission", ""));
            return;
        }
    }

    private ConfigurationSection getCommandSection() {
        FileConfiguration config = this.plugin.getConfig();
        return config.getConfigurationSection("command-bingo");
    }
}
