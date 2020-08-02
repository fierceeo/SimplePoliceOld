package com.voidcitymc.plugins.SimplePolice;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.List;

public class CustomJailGuiItem {

    public boolean isItemNull(int i) {
        if ( SPPlugin.getInstance().getConfig().getList("ArrestGUI").get(i) == null) {
            return true;
        } else {
            return false;
        }
    }

    public double getJailTime(int i) {
        if (!this.isItemNull(i)) {
            return (Double) ((HashMap<String, Object>) (List<Object>) SPPlugin.getInstance().getConfig().getList("ArrestGUI").get(i)).get("JailTime");
        } else {
            return 0.0;
        }
    }

    public Material getMaterial(int i) {
        if (!this.isItemNull(i)) {
            return (Material) ((HashMap<String, Object>) (List<Object>) SPPlugin.getInstance().getConfig().getList("ArrestGUI").get(i)).get("Material");
        } else {
            return Material.AIR;
        }
    }

    public String getPerm(int i) {
        if (!this.isItemNull(i)) {
            return (String) ((HashMap<String, Object>) (List<Object>) SPPlugin.getInstance().getConfig().getList("ArrestGUI").get(i)).get("Perm");
        } else {
            return null;
        }
    }


}
