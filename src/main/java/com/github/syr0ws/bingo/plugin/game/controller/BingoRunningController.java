package com.github.syr0ws.bingo.plugin.game.controller;

import com.github.syr0ws.bingo.api.game.Game;
import com.github.syr0ws.bingo.api.game.model.*;
import com.github.syr0ws.bingo.api.message.Message;
import com.github.syr0ws.bingo.api.message.MessageData;
import com.github.syr0ws.bingo.api.message.MessageType;
import com.github.syr0ws.bingo.api.minigame.MiniGameModel;
import com.github.syr0ws.bingo.api.minigame.MiniGamePlugin;
import com.github.syr0ws.bingo.api.settings.GameSettings;
import com.github.syr0ws.bingo.api.settings.MutableSetting;
import com.github.syr0ws.bingo.plugin.controller.AbstractGameController;
import com.github.syr0ws.bingo.plugin.game.listener.GameRunningListener;
import com.github.syr0ws.bingo.plugin.message.GameMessageKey;
import com.github.syr0ws.bingo.plugin.message.GameMessageType;
import com.github.syr0ws.bingo.plugin.tool.ListenerManager;
import com.github.syr0ws.bingo.plugin.tool.Task;
import com.github.syr0ws.bingo.plugin.tool.Text;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.Set;

public class BingoRunningController extends AbstractGameController {

    private Task task;

    public BingoRunningController(MiniGamePlugin plugin, Game game) {
        super(plugin, game);
    }

    @Override
    public void load() {
        super.load();
        this.startRunningTask();
    }

    @Override
    public void unload() {
        super.unload();
        this.stopRunningTask();
    }

    @Override
    public void registerListeners(ListenerManager manager) {

        GameModel model = super.getGame().getModel();

        manager.registerListener(new GameRunningListener(super.getPlugin(), model));
    }

    @Override
    public GameState getState() {
        return GameState.RUNNING;
    }

    @Override
    public void onMessageReceiving(Message message) {

        MessageType type = message.getType();
        MessageData data = message.getData();

        if(type == GameMessageType.ITEM_FOUND) {

            this.onMessageItemFound(data);

        } else if(type == GameMessageType.WIN) {

            this.onMessageWin(data);
        }
    }

    private void startRunningTask() {

        MiniGamePlugin plugin = super.getPlugin();

        MiniGameModel miniGameModel = plugin.getModel();
        GameSettings settings = miniGameModel.getSettings();

        MutableSetting<Integer> setting = settings.getMaxGameDurationSetting();

        this.task = new RunningTask(plugin, setting.getValue());
        this.task.start();
    }

    private void stopRunningTask() {

        // Stopping task if it is running.
        if(this.task != null && this.task.isRunning()) {
            this.task.cancel();
            this.task = null; // Avoid reuse.
        }
    }

    private void onMessageItemFound(MessageData data) {

        GamePlayer gamePlayer = data.get(GameMessageKey.PLAYER.getKey(), GamePlayer.class);
        GamePlayerGrid playerGrid = data.get(GameMessageKey.PLAYER_GRID.getKey(), GamePlayerGrid.class);

        @SuppressWarnings("unchecked")
        Set<GridLine> lines = data.get(GameMessageKey.GRID_LINES.getKey(), Set.class);

        this.handleItemFound(gamePlayer, playerGrid, lines);
    }

    private void onMessageWin(MessageData data) {

        GamePlayer gamePlayer = data.get(GameMessageKey.PLAYER.getKey(), GamePlayer.class);

        this.handleWin(gamePlayer);
    }

    private void handleItemFound(GamePlayer gamePlayer, GamePlayerGrid playerGrid, Set<GridLine> lines) {

        GameModel model = super.getGame().getModel();

        // Retrieving completed lines.
        int completedLines = playerGrid.countCompletedLines();
        completedLines -= lines.size();

        // Retrieving lines to complete setting.
        MiniGameModel miniGameModel = super.getPlugin().getModel();
        GameSettings settings = miniGameModel.getSettings();

        int linesToComplete = settings.getLinesToCompleteSetting().getValue();

        // Sending messages.
        String itemFoundMessage = String.format(Text.ITEM_FOUND.get(), gamePlayer.getName());
        model.getOnlinePlayers().forEach(player -> player.sendMessage(itemFoundMessage));

        if(lines.contains(GridLine.ROW)) {
            String message = String.format(Text.ROW_COMPLETE.get(), gamePlayer.getName(), ++completedLines, linesToComplete);
            model.getOnlinePlayers().forEach(player -> player.sendMessage(message));
        }

        if(lines.contains(GridLine.COLUMN)) {
            String message = String.format(Text.COLUMN_COMPLETE.get(), gamePlayer.getName(), ++completedLines, linesToComplete);
            model.getOnlinePlayers().forEach(player -> player.sendMessage(message));
        }

        if(lines.contains(GridLine.DIAGONAL)) {
            String message = String.format(Text.DIAGONAL_COMPLETE.get(), gamePlayer.getName(), ++completedLines, linesToComplete);
            model.getOnlinePlayers().forEach(player -> player.sendMessage(message));
        }
    }

    private void handleWin(GamePlayer winner) {

        GameModel model = super.getGame().getModel();

        String bingoCompleteMessage = String.format(Text.PLAYER_BINGO_COMPLETE.get(), winner.getName());
        String bingoWinMessage = String.format(Text.PLAYER_WIN.get(), winner.getName());

        model.getOnlinePlayers().forEach(player -> {

            player.sendMessage(bingoCompleteMessage);

            // Magic values. Can't be changed without an appropriate API.
            player.sendTitle(" ", bingoWinMessage, 10, 70, 20);
        });

        super.sendDoneMessage();
    }

    private void handleWin() {

        GameModel model = super.getGame().getModel();
        Map<GamePlayer, Integer> players = model.getPlayersWithMostFoundItems();

        for(GamePlayer gamePlayer : players.keySet()) {

            String bingoWinMessage = String.format(Text.PLAYER_WIN.get(), gamePlayer.getName());
            model.getOnlinePlayers().forEach(player -> player.sendMessage(bingoWinMessage));
        }

        super.sendDoneMessage();
    }

    private class RunningTask extends Task {

        private final int duration;

        public RunningTask(Plugin plugin, int duration) {
            super(plugin);

            if(duration <= 0)
                throw new IllegalArgumentException("Duration must be positive.");

            this.duration = duration;
        }

        @Override
        public void start() {
            super.start();
            this.runTaskTimer(super.getPlugin(), 0L, 20L);
        }

        @Override
        public void run() {

            GameModel model = getGame().getModel();

            String message = null;

            switch (model.getTime()) {
                case 60:
                    message = String.format(Text.GAME_TIME_LEFT_MINUTE.get(), 4, "s");
                    break;
                case 120:
                    message = String.format(Text.GAME_TIME_LEFT_MINUTE.get(), 3, "s");
                    break;
                case 180:
                    message = String.format(Text.GAME_TIME_LEFT_MINUTE.get(), 2, "s");
                    break;
                case 240:
                    message = String.format(Text.GAME_TIME_LEFT_MINUTE.get(), 1, "");
                    break;
                case 270:
                    message = String.format(Text.GAME_TIME_LEFT_SECOND.get(), 30, "s");
                    break;
                case 290:
                    message = String.format(Text.GAME_TIME_LEFT_SECOND.get(), 10, "s");
                    break;
                case 297:
                    message = String.format(Text.GAME_TIME_LEFT_SECOND.get(), 3, "s");
                    break;
                case 298:
                    message = String.format(Text.GAME_TIME_LEFT_SECOND.get(), 2, "s");
                    break;
                case 299:
                    message = String.format(Text.GAME_TIME_LEFT_SECOND.get(), 1, "");
                    break;
            }

            if(message != null) {
                String finalMessage = message;
                model.getOnlinePlayers().forEach(player -> player.sendMessage(finalMessage));
            }

            if(model.getTime() == this.duration) {

                this.cancel();
                BingoRunningController.this.handleWin();

            } else model.addTime();
        }
    }
}
