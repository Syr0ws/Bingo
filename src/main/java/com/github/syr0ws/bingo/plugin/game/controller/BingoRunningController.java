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
        Bukkit.broadcastMessage("Running");
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

    private void handleItemFound(GamePlayer player, Set<GridLine> lines) {

        GameModel model = super.getGame().getModel();

        model.getPlayers().forEach(gamePlayer -> gamePlayer.getPlayer().sendMessage(String.format("§6%s a trouvé un item !", player.getName())));

        if(lines.contains(GridLine.ROW))
            model.getPlayers().forEach(gamePlayer -> gamePlayer.getPlayer().sendMessage(String.format("§6%s a complété une ligne !", player.getName())));

        if(lines.contains(GridLine.COLUMN))
            model.getPlayers().forEach(gamePlayer -> gamePlayer.getPlayer().sendMessage(String.format("§6%s a complété une colonne !", player.getName())));

        if(lines.contains(GridLine.DIAGONAL))
            model.getPlayers().forEach(gamePlayer -> gamePlayer.getPlayer().sendMessage(String.format("§6%s a complété une diagonale !", player.getName())));
    }
}
