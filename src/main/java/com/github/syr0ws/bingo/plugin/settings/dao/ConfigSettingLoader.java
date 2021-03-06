package com.github.syr0ws.bingo.plugin.settings.dao;

import com.github.syr0ws.bingo.api.settings.Setting;
import com.github.syr0ws.bingo.api.settings.SettingLoader;
import com.github.syr0ws.bingo.plugin.settings.config.Configurable;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Collection;

public class ConfigSettingLoader implements SettingLoader {

    private final ConfigurationSection section;

    public ConfigSettingLoader(ConfigurationSection section) {

        if(section == null)
            throw new IllegalArgumentException("ConfigurationSection cannot be null.");

        this.section = section;
    }

    @Override
    public void load(Collection<Setting<?>> settings) {
        settings.stream()
                .filter(setting -> setting instanceof Configurable)
                .map(setting -> (Configurable) setting)
                .forEach(configurable -> configurable.read(this.section));
    }
}
