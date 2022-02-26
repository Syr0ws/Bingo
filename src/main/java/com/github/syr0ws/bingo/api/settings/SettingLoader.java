package com.github.syr0ws.bingo.api.settings;

import java.util.Collection;

public interface SettingLoader {

    void load(Collection<Setting<?>> settings);
}
