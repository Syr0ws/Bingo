package com.github.syr0ws.bingo.plugin.minigame.listener;

import com.github.syr0ws.bingo.api.game.Game;
import com.github.syr0ws.bingo.api.game.model.GameModel;
import com.github.syr0ws.bingo.api.game.model.GamePlayer;
import com.github.syr0ws.bingo.api.minigame.MiniGameModel;
import com.github.syr0ws.bingo.api.minigame.MiniGamePlugin;
import com.github.syr0ws.bingo.plugin.game.model.BingoGamePlayer;
import com.github.syr0ws.bingo.plugin.tool.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.UUID;

public class BingoMiniGameListener implements Listener {

    private final MiniGamePlugin plugin;
    private final MiniGameModel model;

    public BingoMiniGameListener(MiniGamePlugin plugin) {

        if(plugin == null)
            throw new IllegalArgumentException("MiniGamePlugin cannot be null.");

        this.plugin = plugin;
        this.model = plugin.getModel();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        // Player already in game. GameController will do the job.
        if(this.model.hasGame(uuid)) return;

        Optional<Game> optional = this.model.getWaitingGame();

        // Should not happen here.
        if(optional.isEmpty()) return;

        Game waitingGame = optional.get();
        GameModel waitingGameModel = waitingGame.getModel();

        GamePlayer gamePlayer = new BingoGamePlayer(player);

        // Adding player to the waiting game.
        waitingGameModel.addPlayer(gamePlayer);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        Optional<Game> optional = this.model.getWaitingGame();

        // No waiting game. Player is handled by another controller.
        if(optional.isEmpty()) return;

        Game waitingGame = optional.get();
        GameModel waitingGameModel = waitingGame.getModel();

        // If it exists, removing player from the waiting game.
        Optional<GamePlayer> optionalPlayer = waitingGameModel.getPlayer(uuid);
        optionalPlayer.ifPresent(waitingGameModel::removePlayer);
    }

    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {

        Plugin plugin = event.getPlugin();

        if(!plugin.equals(this.plugin)) return;

        String message = Text.RESTART.get();

        // Kicking all the online players.
        Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer(message));
    }
}
