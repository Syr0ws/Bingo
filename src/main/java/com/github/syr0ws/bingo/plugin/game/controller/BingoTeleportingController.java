package com.github.syr0ws.bingo.plugin.game.controller;

import com.github.syr0ws.bingo.api.game.Game;
import com.github.syr0ws.bingo.api.game.model.GameModel;
import com.github.syr0ws.bingo.api.game.model.GameState;
import com.github.syr0ws.bingo.api.message.Message;
import com.github.syr0ws.bingo.api.message.MessageType;
import com.github.syr0ws.bingo.plugin.game.listener.GameTeleportingListener;
import com.github.syr0ws.bingo.plugin.message.GameMessageType;
import com.github.syr0ws.bingo.plugin.tool.ListenerManager;
import com.github.syr0ws.bingo.plugin.tool.Task;
import com.github.syr0ws.bingo.plugin.tool.TeleportationTask;
import com.github.syr0ws.bingo.plugin.controller.AbstractGameController;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

public class BingoTeleportingController extends AbstractGameController {

    private Task task;

    public BingoTeleportingController(Plugin plugin, Game game) {
        super(plugin, game);
    }

    @Override
    public void load() {
        super.load();
        this.startTeleportationTask();
    }

    @Override
    public void unload() {
        super.unload();
        this.stopTeleportationTask();
    }

    @Override
    public void registerListeners(ListenerManager manager) {

        GameModel model = super.getGame().getModel();

        manager.registerListener(new GameTeleportingListener(model));
    }

    @Override
    public GameState getState() {
        return GameState.TELEPORTING;
    }

    @Override
    public void onMessageReceiving(Message message) {

        MessageType type = message.getType();

        if(type == GameMessageType.TELEPORTATION_FINISHED) super.sendDoneMessage();
    }

    private void startTeleportationTask() {

        GameModel model = super.getGame().getModel();
        World world = Bukkit.getWorld("world");

        this.task = new TeleportationTask(super.getPlugin(), this, model.getPlayers(), world, 150);
        this.task.start();
    }

    private void stopTeleportationTask() {

        if(this.task != null && this.task.isRunning()) {
            this.task.stop();
            this.task = null;
        }
    }
}
