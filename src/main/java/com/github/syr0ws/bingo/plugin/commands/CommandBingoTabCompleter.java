package com.github.syr0ws.bingo.plugin.commands;

import com.github.syr0ws.bingo.api.game.Game;
import com.github.syr0ws.bingo.api.minigame.MiniGameModel;
import com.github.syr0ws.bingo.plugin.tool.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.*;

public class CommandBingoTabCompleter implements TabCompleter {

    private final MiniGameModel model;

    public CommandBingoTabCompleter(MiniGameModel model) {

        if(model == null)
            throw new IllegalStateException("MiniGameModel cannot be null.");

        this.model = model;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        List<String> completions = new ArrayList<>();

        if(!(sender instanceof Player player))
            return completions;

        if(!player.hasPermission(Permission.COMMAND_BINGO.getPermission()))
            return completions;

        completions.addAll(this.onStartCommand(player, args));
        completions.addAll(this.onStopCommand(player, args));

        return completions;
    }

    private List<String> onStartCommand(Player player, String[] args) {

        List<String> completions = new ArrayList<>();

        if(!player.hasPermission(Permission.COMMAND_BINGO_START.getPermission()))
            return completions;

        if(args.length == 1)
            return Collections.singletonList("start");

        return completions;
    }

    private List<String> onStopCommand(Player player, String[] args) {

        List<String> completions = new ArrayList<>();

        if(!player.hasPermission(Permission.COMMAND_BINGO_STOP.getPermission()))
            return completions;

        if(args.length == 1)
            return Collections.singletonList("stop");

        // Command '/bingo stop <id>'.
        if(!args[0].equalsIgnoreCase("stop") || args.length != 2)
            return completions;

        Collection<Game> games = this.model.getGames();
        games.forEach(game -> completions.add(game.getId()));

        return completions;
    }
}
