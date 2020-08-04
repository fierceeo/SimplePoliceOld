package com.voidcitymc.plugins.SimplePolice;

import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;


public class Messages {

    public static String getMessage(String path, String... variable) {
        String textToReturn = SPPlugin.getInstance().Message.getString(path);
        if (textToReturn != null) {
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
        } else {
            return "Error with path " + path + " in the message.yml file of simple police, please check that that path is exists";
        }
    }

    public Map<String, String> getMessagesMap() {
        Map<String, Object> map = SPPlugin.getInstance().Data.getValues(false);
        Map<String, String> newMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() instanceof String) {
                newMap.put(entry.getKey(), (String) entry.getValue());
            }
        }
        return newMap;
    }

}
