package com.github.syr0ws.bingo.plugin.settings;

import com.github.syr0ws.bingo.api.settings.Setting;
import com.github.syr0ws.bingo.api.settings.SettingType;
import com.github.syr0ws.bingo.plugin.settings.types.LocationSetting;
import com.github.syr0ws.bingo.plugin.settings.types.MaterialSetting;
import com.github.syr0ws.bingo.plugin.settings.types.SimpleConfigSetting;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;

public enum BingoSettingEnum implements SettingType {

    MIN_PLAYERS {
        @Override
        public Setting<?> getSetting() {
            return new SimpleConfigSetting
                    .Builder<>("minPlayers", 2, "min-players", Integer.class)
                    .withFilter(value -> value >= 1)
                    .build();
        }
    },

    STARTING_DURATION {
        @Override
        public Setting<?> getSetting() {
            return new SimpleConfigSetting
                    .Builder<>("startingDuration", 150, "starting-duration", Integer.class)
                    .withFilter(value -> value >= 0)
                    .build();
        }
    },

    MAX_GAME_DURATION {
        @Override
        public Setting<?> getSetting() {
            return new SimpleConfigSetting
                    .Builder<>("maxGameDuration", 300, "max-game-duration", Integer.class)
                    .withFilter(value -> value > 0)
                    .build();
        }
    },

    INVINCIBILITY_DURATION {
        @Override
        public Setting<?> getSetting() {
            return new SimpleConfigSetting
                    .Builder<>("invincibilityDuration", 15, "invincibility-duration", Integer.class)
                    .withFilter(value -> value >= 0)
                    .build();
        }
    },

    LINES_TO_COMPLETE {
        @Override
        public Setting<?> getSetting() {
            return new SimpleConfigSetting
                    .Builder<>("linesToComplete", 12, "lines-to-complete", Integer.class)
                    .withFilter(value -> value > 0 && value <= 12)
                    .build();
        }
    },

    GAME_SPAWN {
        @Override
        public Setting<?> getSetting() {
            return new LocationSetting
                    .Builder("gameSpawn", new Location(Bukkit.getWorld("world"), 0, 60, 0), "spawn")
                    .withFilter(location -> location.getWorld() != null)
                    .build();
        }
    },

    TELEPORTATION_RADIUS {
        @Override
        public Setting<?> getSetting() {
            return new SimpleConfigSetting
                    .Builder<>("teleportationRadius", 150, "teleportation-radius", Integer.class)
                    .withFilter(value -> value > 10)
                    .build();
        }
    },

    BANNED_ITEMS {
        @Override
        public Setting<?> getSetting() {
            return new MaterialSetting
                    .Builder("bannedItems", new ArrayList<>(), "banned-items")
                    .build();
        }
    };

    public abstract Setting<?> getSetting();
}
