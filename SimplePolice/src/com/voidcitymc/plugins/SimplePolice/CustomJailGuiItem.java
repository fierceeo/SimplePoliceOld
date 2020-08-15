package com.voidcitymc.plugins.SimplePolice;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.Objects;

public class CustomJailGuiItem {
//i goes from 1 to 9

    SPPlugin plugin;

    public CustomJailGuiItem(SPPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean isItemNull(int i) {
        return Objects.requireNonNull(plugin.getConfig().getList("ArrestGUI")).size() < i;
    }

    public double getJailTime(int i) {
        if (!this.isItemNull(i)) {
            return Double.parseDouble(((HashMap<String, String>) plugin.getConfig().getList("ArrestGUI").get(i - 1)).get("JailTime"));
        } else {
            return 0.0;
        }
    }

    public Material getMaterial(int i) {
        if (!this.isItemNull(i)) {
            return Material.valueOf(((HashMap<String, String>) plugin.getConfig().getList("ArrestGUI").get(i - 1)).get("Material"));
        } else {
            return Material.AIR;
        }
    }

    public String getPerm(int i) {
        if (!this.isItemNull(i)) {
            return ((HashMap<String, String>) plugin.getConfig().getList("ArrestGUI").get(i - 1)).get("Perm");
        } else {
            return null;
        }
    }

    public String getMsg(int i) {
        if (!this.isItemNull(i)) {
            return ((HashMap<String, String>) plugin.getConfig().getList("ArrestGUI").get(i - 1)).get("Message");
        } else {
            return null;
        }
    }

    /*
        - Material: AIR
        - JailTime:
        - Perm:
        - Message:
     */
    public HashMap<String, String> setFile(String mat, Double jailTime, String perm, String msg) {
        HashMap<String, String> mappy = new HashMap<>();
        mappy.put("Material", mat);
        mappy.put("JailTime", String.valueOf(jailTime));
        mappy.put("Perm", perm);
        mappy.put("Message", msg);
        return mappy;
    }


}
