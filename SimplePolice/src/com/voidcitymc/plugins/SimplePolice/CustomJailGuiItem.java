package com.voidcitymc.plugins.SimplePolice;

import it.unimi.dsi.fastutil.Hash;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class CustomJailGuiItem {
//i goes from 1 to 9



    public boolean isItemNull(int i) {
        return Objects.requireNonNull(SPPlugin.getInstance().getConfig().getList("ArrestGUI")).size() < i;
    }

    public double getJailTime(int i) {
        if (!this.isItemNull(i)) {
            return Double.parseDouble(((HashMap<String, String>) SPPlugin.getInstance().getConfig().getList("ArrestGUI").get(i-1)).get("JailTime"));
        } else {
            return 0.0;
        }
    }

    public Material getMaterial(int i) {
        if (!this.isItemNull(i)) {
            return Material.valueOf(((HashMap<String, String>) SPPlugin.getInstance().getConfig().getList("ArrestGUI").get(i-1)).get("Material"));
        } else {
            return Material.AIR;
        }
    }

    public String getPerm(int i) {
        if (!this.isItemNull(i)) {
            return ((HashMap<String, String>) SPPlugin.getInstance().getConfig().getList("ArrestGUI").get(i-1)).get("Perm");
        } else {
            return null;
        }
    }
/*
    - Material: AIR
    - JailTime:
    - Perm:
 */
    public HashMap<String, String> setFile(String mat, Double jailTime, String perm) {
        HashMap<String, String> mappy = new HashMap<>();
        mappy.put("Material", mat);
        mappy.put("JailTime", String.valueOf(jailTime));
        mappy.put("Perm", perm);
        return mappy;
    }


}
