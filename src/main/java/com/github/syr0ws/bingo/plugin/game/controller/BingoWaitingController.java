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
import com.github.syr0ws.bingo.plugin.tool.Text;
import com.github.syr0ws.bingo.plugin.util.TextUtil;
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
        this.stopStartingTask();
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

    private void stopStartingTask() {

        // Stopping task if it is running.
        if(this.isStarting()) {
            this.task.cancel();
            this.task = null; // Avoid reuse.
        }
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

                String message = String.format(Text.GAME_STARTING_IN.get(), this.time);

                model.getOnlinePlayers().forEach(player -> {

                    // Magic values. Can't be changed without an appropriate API.
                    player.sendTitle(" ", message, 10, 70, 20);
                    player.sendMessage(message);
                });

                this.time--;

            } else {

                this.stop();

                String message = Text.GAME_STARTED.get();

                model.getOnlinePlayers().forEach(player -> {

                    // Magic values. Can't be changed without an appropriate API.
                    player.sendTitle(" ", message, 10, 70, 20);
                    player.sendMessage(message);
                });

                BingoWaitingController.super.sendDoneMessage();
            }
        }
    }
}
