package com.github.syr0ws.bingo.plugin.settings.types;

import org.bukkit.configuration.ConfigurationSection;

import java.util.Objects;

public class EnumSetting<E extends Enum<E>> extends ConfigSetting<Enum<E>> {

    private final Class<E> clazz;

    protected EnumSetting(String name, Enum<E> defaultValue, Enum<E> value, Class<E> clazz, String path) {
        super(name, defaultValue, value, Objects::nonNull, path);
        this.clazz = clazz;
    }

    @Override
    public void read(ConfigurationSection section) {

        String value = section.getString(super.getPath());

        if(value == null)
            throw new IllegalArgumentException(String.format("No enum value found at '%s.%s'.", section.getName(), super.getPath()));

        Enum<E> enumValue = Enum.valueOf(this.clazz, value);

        super.setValue(enumValue);
    }

    public static class Builder<E extends Enum<E>> extends ConfigSetting.Builder<Enum<E>> {

        private final Class<E> clazz;

        public Builder(String name, Enum<E> defaultValue, Class<E> clazz, String path) {
            super(name, defaultValue, path);
            this.clazz = clazz;
        }

        @Override
        public EnumSetting<E> build() {
            return new EnumSetting<>(
                    super.getName(),
                    super.getDefaultValue(),
                    super.getValue(),
                    this.clazz,
                    super.getPath()
            );
        }
    }
}
