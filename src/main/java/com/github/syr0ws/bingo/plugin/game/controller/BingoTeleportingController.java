package com.github.syr0ws.bingo.plugin.game.controller;

import com.github.syr0ws.bingo.api.game.Game;
import com.github.syr0ws.bingo.api.game.model.GameModel;
import com.github.syr0ws.bingo.api.game.model.GameState;
import com.github.syr0ws.bingo.api.message.Message;
import com.github.syr0ws.bingo.api.message.MessageType;
import com.github.syr0ws.bingo.api.minigame.MiniGameModel;
import com.github.syr0ws.bingo.api.minigame.MiniGamePlugin;
import com.github.syr0ws.bingo.api.settings.GameSettings;
import com.github.syr0ws.bingo.api.settings.MutableSetting;
import com.github.syr0ws.bingo.plugin.controller.AbstractGameController;
import com.github.syr0ws.bingo.plugin.game.listener.GameTeleportingListener;
import com.github.syr0ws.bingo.plugin.message.GameMessageType;
import com.github.syr0ws.bingo.plugin.tool.ListenerManager;
import com.github.syr0ws.bingo.plugin.tool.Task;
import com.github.syr0ws.bingo.plugin.tool.TeleportationTask;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class BingoTeleportingController extends AbstractGameController {

    private Task task;

    public BingoTeleportingController(MiniGamePlugin plugin, Game game) {
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

        MiniGameModel miniGameModel = super.getPlugin().getModel();

        GameSettings settings = miniGameModel.getSettings();
        MutableSetting<Integer> setting = settings.getTeleportationRadiusSetting();

        int radius = setting.getValue();

        this.task = new TeleportationTask(super.getPlugin(), this, model.getPlayers(), world, radius);
        this.task.start();
    }

    private void stopTeleportationTask() {

        if(this.task != null && this.task.isRunning()) {
            this.task.stop();
            this.task = null;
        }
    }
}
