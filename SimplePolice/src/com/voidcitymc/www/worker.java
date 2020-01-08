package com.voidcitymc.www;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;


public class worker {
	

	
//check if a player has item in hand
public static boolean TestForItem(Player p, Material item, String DisplayName) {
        if (item != null && p.getInventory().getItemInMainHand().getType() == item) {
            return true;
        }
        return false;
    }

public String playerToUUID (String player) {
	return Bukkit.getPlayer(player).getUniqueId().toString();
}
public String playerToString (Player player) {
	return player.toString();
}

public void addPolice (String uuid) {
	if (alreadyPolice(uuid)) {
		return;
	} else if (!alreadyPolice(uuid)) {
		Main.getInstance().getConfig().addDefault(uuid, true);
		Main.getInstance().saveConfig();
		Main.getInstance().getConfig().options().copyDefaults(true);
		Main.getInstance().saveConfig();
		return;
	} else {
		return;
	}
}

public boolean alreadyPolice (String uuid) {
	if (Main.getInstance().getConfig().getBoolean(uuid)) {
		return true;
	} else {
		return false;
	}
}

public void removePolice(String uuid) {
	worker testPoliceVar = new worker();
	if (testPoliceVar.alreadyPolice(uuid)) {
		Main.getInstance().getConfig().addDefault(uuid, false);
		Main.getInstance().saveConfig();
		return;
	} else {
		return;
	}
}





}
