package com.voidcitymc.plugins.SimplePolice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.bukkit.Location;
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


public void addPolice (String uuid) {
	if (alreadyPolice(uuid)) {
		return;
	} else if (!alreadyPolice(uuid)) {
		Main.getInstance().Data.set(uuid, true);
		Main.getInstance().SaveDataFile();
		return;
	} else {
		return;
	}
}

public boolean alreadyPolice (String uuid) {
	if (Main.getInstance().Data.getBoolean(uuid)) {
		return true;
	} else {
		return false;
	}
}

public void removePolice(String uuid) {
	worker testPoliceVar = new worker();
	if (testPoliceVar.alreadyPolice(uuid)) {
		Main.getInstance().Data.set(uuid, false);
		Main.getInstance().SaveDataFile();;
		return;
	} else {
		return;
	}
}

public static boolean isLocationSafe (Location loc1) {
	//check if the location is safe to teleport to (air)
	if (loc1.getBlock().getType().equals(Material.AIR)) {
		//we know that the inital location is safe, so we need to check one block up
		//location one block up
		Location loc2 = new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+1, loc1.getZ());
		if (loc2.getBlock().getType().equals(Material.AIR)) {
			//2nd block is safe too
			return true;
		}
		//loc2 didn't check out 
		return false;
	}
	//because that method didn't pass, we know that the location isn't safe
	return false;
}

public static Location policeTp (Player player, int MaxValTp) {
	Location LocP = player.getLocation();
	//players x,y,z
	int pX = LocP.getBlockX();
	int pY = LocP.getBlockY();
	int pZ = LocP.getBlockZ();
	
	//generate a random number from 0 to the farthest /police tp value in config (MaxValTp)
    double drandom1 = Math.random() * MaxValTp;
    double drandom2 = Math.random() * MaxValTp;
    int random1 = (int) Math.round(drandom1);
    int random2 = (int) Math.round(drandom2);
    
    //should number be negative or positave
    
    //number from 0 to 1
    int posOrNeg = (int) Math.round(Math.random());
    if (posOrNeg == 1) {
    	random1 = random1 - (2 * random1);
    }
    
    posOrNeg = (int) Math.round(Math.random());
    if (posOrNeg == 1) {
    	random2 = random2 - (2 * random2);
    }
    
    
	
    int nX = pX+random1;
    int nY = pY;
    int nZ = pZ+random2;
    
    Location returnLoc = new Location(player.getWorld(), nX, nY, nZ);
    Location returnLocYOneDown = new Location(player.getWorld(), nX, nY-1, nZ);
    
    while (returnLocYOneDown.getBlock().getType().equals(Material.AIR)) {
    	nY = nY-1;
    	returnLocYOneDown = new Location(player.getWorld(), nX, nY-1, nZ);
    }
    
    returnLoc = returnLocYOneDown;
    
    
    //keeps increasing y cord until location is safe
    while (!isLocationSafe(returnLoc)) {
    	nY = nY+1;
    	returnLoc = new Location(player.getWorld(), nX, nY, nZ);
    }
	
    return returnLoc;
    
	
}

public static ArrayList<String> ListPolice() {
	
	Map<String, Object> police = Main.getInstance().Data.getValues(false);
	ArrayList<Object> policeListNotFinal = new ArrayList<Object>();

	ArrayList<String> policeList = new ArrayList<String>();
	
	policeListNotFinal = (ArrayList<Object>) Arrays.asList(police.keySet().toArray());
	
	int i = 0;
	
	while (i <= policeListNotFinal.size()) {
		if (police.get(policeListNotFinal.get(i)) instanceof Boolean) {
			if ((boolean) police.get(policeListNotFinal.get(i))) {
				policeList.add((String) policeListNotFinal.get(i));
			}
		}
		i++;
	}
	
	return policeList;
}



}
