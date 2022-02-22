package com.github.syr0ws.bingo.plugin.game.controller;

import com.github.syr0ws.bingo.api.game.Game;
import com.github.syr0ws.bingo.api.game.model.GameModel;
import com.github.syr0ws.bingo.api.game.model.GameState;
import com.github.syr0ws.bingo.api.message.Message;
import com.github.syr0ws.bingo.api.message.MessageType;
import com.github.syr0ws.bingo.plugin.game.listener.GameWaitingListener;
import com.github.syr0ws.bingo.plugin.message.GameMessageType;
import com.github.syr0ws.bingo.plugin.tool.ListenerManager;
import com.github.syr0ws.bingo.plugin.tool.Task;
import com.github.syr0ws.bingo.plugin.controller.AbstractGameController;
import org.bukkit.plugin.Plugin;

public class BingoWaitingController extends AbstractGameController {

    private Task task;

    public BingoWaitingController(Plugin plugin, Game game) {
        super(plugin, game);
    }

    @Override
    public void load() {
        super.load();
    }

    @Override
    public void unload() {
        super.unload();

        // Stopping task if it is running.
        if(this.isStarting()) {
            this.task.cancel();
            this.task = null; // Avoid reuse.
        }
    }

    @Override
    public void registerListeners(ListenerManager manager) {

        GameModel model = super.getGame().getModel();

        manager.registerListener(new GameWaitingListener(model));
    }

    @Override
    public GameState getState() {
        return GameState.WAITING;
    }

    @Override
    public void onMessageReceiving(Message message) {

        MessageType type = message.getType();

        if(type == GameMessageType.START_GAME && !this.isStarting()) this.startStartingTask();
    }

    private void startStartingTask() {
        this.task = new StartTask(super.getPlugin(), 3);
        this.task.start();
    }

    private boolean isStarting() {
        return this.task != null && this.task.isRunning();
    }

    private class StartTask extends Task {

        private int time;

        public StartTask(Plugin plugin, int time) {
            super(plugin);

            if(time < 0)
                throw new IllegalArgumentException("Time cannot be negative.");

            this.time = time;
        }

        @Override
        public void start() {
            super.start();
            this.runTaskTimer(super.getPlugin(), 0L, 20L);
        }

        @Override
        public void run() {

            GameModel model = BingoWaitingController.super.getGame().getModel();

            if(this.time != 0) {

                String message = String.format("§eDébut dans §6%d§e.", this.time);
                model.getPlayers().forEach(gamePlayer -> gamePlayer.getPlayer().sendMessage(message));

                this.time--;

            } else {

                this.stop();

                String message = "§6Début de la partie !";
                model.getPlayers().forEach(gamePlayer -> gamePlayer.getPlayer().sendMessage(message));

                BingoWaitingController.super.sendDoneMessage();
            }
        }
    }
}
