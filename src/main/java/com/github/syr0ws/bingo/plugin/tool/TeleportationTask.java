package com.github.syr0ws.bingo.plugin.tool;

import com.github.syr0ws.bingo.api.game.model.GamePlayer;
import com.github.syr0ws.bingo.api.message.Message;
import com.github.syr0ws.bingo.api.message.Observer;
import com.github.syr0ws.bingo.plugin.message.GameMessage;
import com.github.syr0ws.bingo.plugin.message.GameMessageType;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class TeleportationTask extends Task {

    private final int radius;
    private final World world;
    private final List<GamePlayer> players;
    private final double delta;
    private final Observer observer;
    private double angle;
    private int index;

    public TeleportationTask(Plugin plugin, Observer observer, List<GamePlayer> players, World world, int radius) {
        super(plugin);

        if(observer == null)
            throw new IllegalArgumentException("Observer cannot be null.");

        if(players == null)
            throw new IllegalArgumentException("List<GamePlayer> cannot be null.");

        if(world == null)
            throw new IllegalArgumentException("GamePlayer cannot be null.");

        if(radius <= 0)
            throw new IllegalArgumentException("Radius cannot be negative.");

        this.observer = observer;
        this.players = players;
        this.world = world;
        this.radius = radius;

        this.delta = (2 * Math.PI) / (double) players.size();
    }

    @Override
    public void start() {
        super.start();
        super.runTaskTimer(super.getPlugin(), 0L, 5L);
    }

    @Override
    public void run() {

        if(this.index < this.players.size()) {

            GamePlayer gamePlayer = this.players.get(this.index);
            Player player = gamePlayer.getPlayer();

            this.teleport(player);

            this.index++;

        } else {

            Message message = new GameMessage(GameMessageType.TELEPORTATION_FINISHED);
            this.observer.onMessageReceiving(message);
        }
    }

    private void teleport(Player player) {

        double x = 0.5 + this.radius * Math.cos(this.angle);
        double z = 0.5 + this.radius * Math.sin(this.angle);
        int y = this.world.getHighestBlockYAt((int) x, (int) z);

        Location location = new Location(this.world, x, y, z);

        player.teleport(location);

        this.angle += this.delta;
    }
}
