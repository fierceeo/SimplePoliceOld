package com.voidcitymc.www;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

//import java.util.*;
//import org.bukkit.command.*;
//import org.bukkit.event.*;
//import org.bukkit.plugin.java.JavaPlugin;
//import org.bukkit.configuration.file.FileConfiguration;

public class worker {
	

	

public String playerToUUID (String player) {
	return Bukkit.getPlayer(player).getUniqueId().toString();
}
public String playerToString (Player player) {
	return player.toString();
}

public void addPolice (String uuid) {
	worker testPoliceVar = new worker();
	if (testPoliceVar.allreadyPolice(uuid)) {
		return;
	} else if (!testPoliceVar.allreadyPolice(uuid)) {
		main.getInstance().getConfig().addDefault(uuid, true);
		main.getInstance().saveConfig();
		main.getInstance().getConfig().options().copyDefaults(true);
		main.getInstance().saveConfig();
		return;
	} else {
		return;
	}
}

public boolean allreadyPolice (String uuid) {
	if (main.getInstance().getConfig().getBoolean(uuid)) {
		return true;
	} else {
		return false;
	}
}

public void removePolice(String uuid) {
	worker testPoliceVar = new worker();
	if (testPoliceVar.allreadyPolice(uuid)) {
		main.getInstance().getConfig().addDefault(uuid, false);
		main.getInstance().saveConfig();
		return;
	} else {
		return;
	}
}





}


// https://www.spigotmc.org/threads/getconfig-outside-main-class.117854/?__cf_chl_jschl_tk__=243e49cdca59cc0a8fcc1edf2aaaf25be23203d5-1575926286-0-Aalx2i7iXifBWc6XR9i3zRp88ZD4TfVHYda2ZJLBKOLE2YlAdqbaipspC3mhjzy1svz_ygInJM9NKggkxB6Q4PismhSL4HB2Q1ggEvPXVTGViINkQqHQJCM9IoYVPuPvJD_JHfToN5wM1uiIZyjyaUjHZ0Ulxv5irwajC3_4u6E3KDjwoKM1pAI_jAJ6oAGZjGuR8ByTtXVwNAvc87yl9L7y59GIg1X9PAsofMIXTnPpkAp0wCi6kzhgfTJ9ANkSnI-hWTZM4dKGwefs2Iv1R2JhQUl23I-u1nfapprBuyINYlCex1MAp-AZBfvMiEFgcw
