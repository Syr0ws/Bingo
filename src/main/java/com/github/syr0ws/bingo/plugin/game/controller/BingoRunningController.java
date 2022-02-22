package com.github.syr0ws.bingo.plugin.game.controller;

import com.github.syr0ws.bingo.api.game.Game;
import com.github.syr0ws.bingo.api.game.model.GameModel;
import com.github.syr0ws.bingo.api.game.model.GamePlayer;
import com.github.syr0ws.bingo.api.game.model.GameState;
import com.github.syr0ws.bingo.api.game.model.GridLine;
import com.github.syr0ws.bingo.api.message.Message;
import com.github.syr0ws.bingo.api.message.MessageData;
import com.github.syr0ws.bingo.api.message.MessageType;
import com.github.syr0ws.bingo.plugin.game.listener.GameRunningListener;
import com.github.syr0ws.bingo.plugin.message.GameMessageKey;
import com.github.syr0ws.bingo.plugin.message.GameMessageType;
import com.github.syr0ws.bingo.plugin.tool.ListenerManager;
import com.github.syr0ws.bingo.plugin.controller.AbstractGameController;
import com.github.syr0ws.bingo.plugin.tool.Text;
import com.github.syr0ws.bingo.plugin.util.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Set;

public class BingoRunningController extends AbstractGameController {

    public BingoRunningController(Plugin plugin, Game game) {
        super(plugin, game);
    }

    @Override
    public void load() {
        super.load();
    }

    @Override
    public void unload() {
        super.unload();
    }

    @Override
    public void registerListeners(ListenerManager manager) {

        GameModel model = super.getGame().getModel();

        manager.registerListener(new GameRunningListener(model));
    }

    @Override
    public GameState getState() {
        return GameState.RUNNING;
    }

    @Override
    public void onMessageReceiving(Message message) {

        MessageType type = message.getType();
        MessageData data = message.getData();

        if(type != GameMessageType.ITEM_FOUND) return;

        GamePlayer gamePlayer = data.get(GameMessageKey.PLAYER.getKey(), GamePlayer.class);

        @SuppressWarnings("unchecked")
        Set<GridLine> lines = data.get(GameMessageKey.GRID_LINES.getKey(), Set.class);

        this.handleItemFound(gamePlayer, lines);
    }

    private void handleItemFound(GamePlayer gamePlayer, Set<GridLine> lines) {

        GameModel model = super.getGame().getModel();

        String itemFoundMessage = String.format(Text.ITEM_FOUND.get(), gamePlayer.getName());

        model.getOnlinePlayers().forEach(player -> player.sendMessage(itemFoundMessage));

        if(lines.contains(GridLine.ROW)) {
            String message = String.format(Text.ROW_COMPLETE.get(), gamePlayer.getName());
            model.getOnlinePlayers().forEach(player -> player.sendMessage(message));
        }

        if(lines.contains(GridLine.COLUMN)) {
            String message = String.format(Text.COLUMN_COMPLETE.get(), gamePlayer.getName());
            model.getOnlinePlayers().forEach(player -> player.sendMessage(message));
        }

        if(lines.contains(GridLine.DIAGONAL)) {
            String message = String.format(Text.DIAGONAL_COMPLETE.get(), gamePlayer.getName());
            model.getOnlinePlayers().forEach(player -> player.sendMessage(message));
        }
    }
}
