package com.github.syr0ws.bingo.api.settings;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.List;

public interface GameSettings {

    MutableSetting<Integer> getMaxGameDurationSetting();

    MutableSetting<Integer> getInvincibilityDurationSetting();

    MutableSetting<Integer> getMinPlayerSetting();

    MutableSetting<Location> getGameSpawnSetting();

    MutableSetting<List<Material>> getBannedItems();
}
