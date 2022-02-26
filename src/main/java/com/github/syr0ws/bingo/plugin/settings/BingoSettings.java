package com.github.syr0ws.bingo.plugin.settings;

import com.github.syr0ws.bingo.api.settings.GameSettings;
import com.github.syr0ws.bingo.api.settings.MutableSetting;
import com.github.syr0ws.bingo.api.settings.SettingLoader;
import com.github.syr0ws.bingo.api.settings.SettingManager;
import com.github.syr0ws.bingo.plugin.settings.dao.ConfigSettingLoader;
import com.github.syr0ws.bingo.plugin.settings.types.LocationSetting;
import com.github.syr0ws.bingo.plugin.settings.types.MaterialSetting;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class BingoSettings implements GameSettings {

    private final SettingManager manager;
    private boolean initialized;

    public BingoSettings(SettingManager manager) {

        if(manager == null)
            throw new IllegalArgumentException("SettingsManager cannot be null.");

        this.manager = manager;
    }

    public void init(FileConfiguration config) {

        if(this.initialized)
            throw new IllegalStateException("Settings already initialized.");

        this.initialized = true;

        for (BingoSettingEnum value : BingoSettingEnum.values())
            this.manager.addSetting(value, value.getSetting());

        SettingLoader loader = new ConfigSettingLoader(config);
        loader.load(this.manager.getSettings());
    }

    public SettingManager getManager() {
        return this.manager;
    }

    @Override
    public MutableSetting<Integer> getMaxGameDurationSetting() {
        return this.manager.getGenericSetting(BingoSettingEnum.MAX_GAME_DURATION, Integer.class);
    }

    @Override
    public MutableSetting<Integer> getStartingDurationSetting() {
        return this.manager.getGenericSetting(BingoSettingEnum.STARTING_DURATION, Integer.class);
    }

    @Override
    public MutableSetting<Integer> getTeleportationRadiusSetting() {
        return this.manager.getGenericSetting(BingoSettingEnum.TELEPORTATION_RADIUS, Integer.class);
    }

    @Override
    public MutableSetting<Integer> getInvincibilityDurationSetting() {
        return this.manager.getGenericSetting(BingoSettingEnum.INVINCIBILITY_DURATION, Integer.class);
    }

    @Override
    public MutableSetting<Integer> getLinesToCompleteSetting() {
        return this.manager.getGenericSetting(BingoSettingEnum.LINES_TO_COMPLETE, Integer.class);
    }

    @Override
    public MutableSetting<Integer> getMinPlayerSetting() {
        return this.manager.getGenericSetting(BingoSettingEnum.MIN_PLAYERS, Integer.class);
    }

    @Override
    public MutableSetting<Location> getGameSpawnSetting() {
        return this.manager.getSetting(BingoSettingEnum.GAME_SPAWN, LocationSetting.class);
    }

    @Override
    public MutableSetting<List<Material>> getBannedItems() {
        return this.manager.getSetting(BingoSettingEnum.BANNED_ITEMS, MaterialSetting.class);
    }
}
