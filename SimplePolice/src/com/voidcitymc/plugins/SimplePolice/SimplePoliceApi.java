package com.voidcitymc.plugins.SimplePolice;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.voidcitymc.api.SimplePolice.SimplePoliceAPI;

public class SimplePoliceApi implements SimplePoliceAPI {

	
	Worker work = new Worker();
	@Override
	public ArrayList<String> onlinePoliceList() {
		return work.onlinePoliceList();
	}

	@Override
	public void addPolice(String uuid) {
		work.addPolice(uuid);
	}

	@Override
	public boolean isPolice(String uuid) {
		return work.alreadyPolice(uuid);
	}

	@Override
	public void removePolice(String uuid) {
		work.removePolice(uuid);
		
	}

	@Override
	public Location policeTp(Player player, int farthestTpDistance) {
		return work.policeTp(player, farthestTpDistance);
	}

	@Override
	public Location policeTp(Player player) {
		return work.policeTp(player, SPPlugin.getInstance().getConfig().getInt("MaxPoliceTp"));
	}

	@Override
	public ArrayList<String> listPolice() {
		return work.listPolice();
	}

	@Override
	public boolean inSafeArea(Player player) {
		return work.inSafeArea(player);
	}
}
