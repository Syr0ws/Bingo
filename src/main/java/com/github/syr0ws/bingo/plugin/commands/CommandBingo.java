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

public class CommandBingo implements CommandExecutor {

    private final MiniGamePlugin plugin;

    public CommandBingo(MiniGamePlugin plugin) {

        if(plugin == null)
            throw new IllegalArgumentException("GamePlugin cannot be null.");

        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!sender.hasPermission(Permission.COMMAND_BINGO.getPermission())) {
            TextUtil.sendMessage(sender, "&cVous n'avez pas la permission d'utiliser cette commande.");
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

        TextUtil.sendMessage(sender, "&6/bingo &7pour voir la liste des commandes.");
        TextUtil.sendMessage(sender, "&6/bingo start &7pour lancer une partie.");
        TextUtil.sendMessage(sender, "&6/bingo stop &e<id> &7pour stopper la partie spécifiée.");
    }

    private void onStartCommand(CommandSender sender, String[] args) {

        if(!sender.hasPermission(Permission.COMMAND_BINGO_START.getPermission())) {
            TextUtil.sendMessage(sender, "&cVous n'avez pas la permission d'utiliser cette commande.");
            return;
        }

        MiniGameModel model = this.plugin.getModel();

        Optional<Game> optional = model.getWaitingGame();

        if(optional.isEmpty()) {
            TextUtil.sendMessage(sender, "&cAucune partie en attente.");
            return;
        }

        Game game = optional.get();

        this.plugin.getController().onGameStart(game);

        TextUtil.sendMessage(sender, "&6Lancement de la partie...");
    }

    private void onStopCommand(CommandSender sender, String[] args) {

        if(!sender.hasPermission(Permission.COMMAND_BINGO_STOP.getPermission())) {
            TextUtil.sendMessage(sender, "&cVous n'avez pas la permission d'utiliser cette commande.");
            return;
        }
    }
}
