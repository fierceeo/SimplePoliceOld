package com.voidcitymc.plugins.SimplePolice;

import org.bukkit.ChatColor;


public class Messages {

    public static String getMessage(String path, String... variable) {
        String textToReturn = SPPlugin.getInstance().Message.getString(path);
        if (variable.length != 0) {
            int i = 0;
            while (i < variable.length) {
                textToReturn = textToReturn.replace("$" + (i + 1), variable[i]);
                i++;
            }
            return ChatColor.translateAlternateColorCodes('&', textToReturn);
        } else {
            return ChatColor.translateAlternateColorCodes('&', textToReturn);
        }
    }

}
