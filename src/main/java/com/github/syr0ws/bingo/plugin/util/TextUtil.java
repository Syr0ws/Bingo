package com.github.syr0ws.bingo.plugin.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TextUtil {

    private static final char COLOR_CHAR = '&';

    public static String parseColors(String text) {

        if(text == null)
            throw new IllegalArgumentException("Text cannot be null.");

        return ChatColor.translateAlternateColorCodes(COLOR_CHAR, text);
    }

    public static void sendMessage(CommandSender sender, String message) {

        if(sender == null)
            throw new IllegalArgumentException("Player cannot be null.");

        if(message == null || message.isEmpty())
            return;

        message = TextUtil.parseColors(message);

        sender.sendMessage(message);
    }
}
