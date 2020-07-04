package com.voidcitymc.plugins.SimplePolice;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SimplePoliceAPI {
	
	Worker work = new Worker();
	
public ArrayList<String> onlinePoliceList() {
	return work.onlinePoliceList();
}
	
public void addPolice (String uuid) {
	work.addPolice(uuid);
}

public boolean isPolice (String uuid) {
	return work.alreadyPolice(uuid);
}

public void removePolice(String uuid) {
	work.removePolice(uuid);
}

public boolean isLocationSafe (Location location) {
	return work.isLocationSafe(location);
}

public Location policeTp (Player player, int farthestTpDistance) {
	return work.policeTp(player, farthestTpDistance);
}

public Location policeTp (Player player) {
	return work.policeTp(player, SPPlugin.getInstance().getConfig().getInt("MaxPoliceTp"));
}

public ArrayList<String> listPolice() {
	return work.listPolice();
}

public boolean inSafeArea (Player player) {
	return work.inSafeArea(player);
}


}
