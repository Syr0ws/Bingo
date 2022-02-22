package com.github.syr0ws.bingo.plugin.commands;

import com.github.syr0ws.bingo.api.game.Game;
import com.github.syr0ws.bingo.api.game.model.GameModel;
import com.github.syr0ws.bingo.api.minigame.MiniGameModel;
import com.github.syr0ws.bingo.api.minigame.MiniGamePlugin;
import com.github.syr0ws.bingo.plugin.tool.Permission;
import com.github.syr0ws.bingo.plugin.util.TextUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
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

        if(!(sender instanceof Player player)) {
            sender.sendMessage("You must be a player to use this command.");
            return true;
        }

        if(!player.hasPermission(Permission.COMMAND_BINGO.getPermission())) {
            this.sendMessage(player, Message.NO_PERMISSION);
            return true;
        }

        if(args.length == 0) {
            this.onBingoGridCommand(player);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "start" -> this.onStartCommand(player);
            case "stop" -> this.onStopCommand(player, args);
            default -> this.sendUsages(player);
        }
        return true;
    }

    private void sendUsages(CommandSender player) {

        FileConfiguration config = this.plugin.getConfig();

        List<String> usages = config.getStringList(Message.USAGES.getPath());
        String[] array = new String[usages.size()];

        for(int i = 0; i < array.length; i++) array[i] = TextUtil.parseColors(usages.get(i));

        player.sendMessage(array);
    }

    private void onBingoGridCommand(Player player) {


    }

    private void onStartCommand(Player player) {

        // Checking that the player has the required permission.
        if(!player.hasPermission(Permission.COMMAND_BINGO_START.getPermission())) {
            this.sendMessage(player, Message.NO_PERMISSION);
            return;
        }

        MiniGameModel miniGameModel = this.plugin.getModel();
        Optional<Game> optional = miniGameModel.getWaitingGame();

        // Checking that there is a waiting game.
        if(optional.isEmpty()) {
            this.sendMessage(player, Message.NO_WAITING_GAME);
            return;
        }

        Game game = optional.get();
        GameModel model = game.getModel();

        // Checking that the game is not already started.
        if(model.isStarting()) {
            this.sendMessage(player, Message.ALREADY_STARTED);
            return;
        }

        // Starting the game.
        this.plugin.getController().onGameStart(game);

        // Sending a message.
        this.sendMessage(player, Message.GAME_STARTING);
    }

    private void onStopCommand(Player player, String[] args) {

        // Checking that the player has the required permission.
        if(!player.hasPermission(Permission.COMMAND_BINGO_STOP.getPermission())) {
            this.sendMessage(player, Message.NO_PERMISSION);
            return;
        }

        // Syntax : /bingo stop <id>
        if(args.length != 2) {
            this.sendUsages(player);
            return;
        }

        String gameId = args[1];

        MiniGameModel miniGameModel = this.plugin.getModel();
        Optional<Game> optional = miniGameModel.getGame(gameId);

        // Checking that the specified game exists.
        if(optional.isEmpty()) {
            this.sendMessage(player, Message.INVALID_GAME);
            return;
        }

        Game game = optional.get();

        // Stopping the game.
        this.plugin.getController().onGameStop(game);

        // Sending a message.
        this.sendMessage(player, Message.GAME_STOPPED);
    }

    private void sendMessage(Player player, Message message) {
        FileConfiguration config = this.plugin.getConfig();
        String text = config.getString(message.getPath(), "");
        TextUtil.sendMessage(player, text);
    }

    private ConfigurationSection getCommandSection() {
        FileConfiguration config = this.plugin.getConfig();
        return config.getConfigurationSection("bingo-command");
    }

    private enum Message {

        NO_PERMISSION("no-permission"),
        NO_WAITING_GAME("no-waiting-game"),
        INVALID_GAME("invalid-game"),

        ALREADY_STARTED("start.already-started"),
        GAME_STARTING("start.starting"),

        GAME_STOPPED("stop.stopped"),

        USAGES("usages");

        private final String path;

        Message(String path) {
            this.path = "command-bingo." + path;
        }

        public String getPath() {
            return this.path;
        }
    }
}