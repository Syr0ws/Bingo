package com.github.syr0ws.bingo.plugin.game.controller;

import com.github.syr0ws.bingo.api.game.Game;
import com.github.syr0ws.bingo.api.game.model.GameModel;
import com.github.syr0ws.bingo.api.game.model.GameState;
import com.github.syr0ws.bingo.api.message.Message;
import com.github.syr0ws.bingo.plugin.controller.AbstractGameController;
import com.github.syr0ws.bingo.plugin.tool.Task;
import com.github.syr0ws.bingo.plugin.tool.Text;
import org.bukkit.plugin.Plugin;

public class BingoFinishedController extends AbstractGameController {

    private Task task;

    public BingoFinishedController(Plugin plugin, Game game) {
        super(plugin, game);
    }

    @Override
    public void load() {
        super.load();
        this.startTask();
    }

    @Override
    public void unload() {
        super.unload();
        this.stopTask();
    }

    @Override
    public GameState getState() {
        return GameState.FINISHED;
    }

    @Override
    public void onMessageReceiving(Message message) {

    }

    private void startTask() {
        this.task = new FinishTask(super.getPlugin());
        this.task.start();
    }

    private void stopTask() {

        // Stopping task if it is running.
        if(this.task != null && this.task.isRunning()) {
            this.task.cancel();
            this.task = null; // Avoid reuse.
        }
    }

    private void onGameFinish() {

        GameModel model = super.getGame().getModel();
        model.getOnlinePlayers().forEach(player -> player.kickPlayer(Text.GAME_FINISHED.get()));

        super.sendDoneMessage();
    }

    private class FinishTask extends Task {

        public FinishTask(Plugin plugin) {
            super(plugin);
        }

        @Override
        public void start() {
            super.start();
            this.runTaskLater(super.getPlugin(), 30 * 20L);
        }

        @Override
        public void run() {
            BingoFinishedController.this.onGameFinish();
        }
    }
}
