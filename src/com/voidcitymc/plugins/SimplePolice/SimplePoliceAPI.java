package com.voidcitymc.plugins.SimplePolice;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SimplePoliceAPI {
	
	static Worker work = new Worker();
	
public static ArrayList<String> onlinePoliceList() {
	return work.onlinePoliceList();
}
	
public static void addPolice (String uuid) {
	work.addPolice(uuid);
}

public static boolean isPolice (String uuid) {
	return work.alreadyPolice(uuid);
}

public static void removePolice(String uuid) {
	work.removePolice(uuid);
}

public static boolean isLocationSafe (Location location) {
	return work.isLocationSafe(location);
}

public static Location policeTp (Player player, int farthestTpDistance) {
	return work.policeTp(player, farthestTpDistance);
}

public static Location policeTp (Player player) {
	return work.policeTp(player, SPPlugin.getInstance().getConfig().getInt("MaxPoliceTp"));
}

public static ArrayList<String> listPolice() {
	return work.listPolice();
}

public static boolean inSafeArea (Player player) {
	return work.inSafeArea(player);
}


}
