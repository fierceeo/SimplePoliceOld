package com.voidcitymc.plugins.SimplePolice;

import net.milkbowl.vault.economy.Economy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;


public class Worker {
	
public ArrayList<String> onlinePoliceList() {
	ArrayList<String> policeList = this.listPolice();
	ArrayList<String> onlinePolice = new ArrayList<String>();
	int i = 0;


	//online police list
	while (i < policeList.size()) {
		if (Bukkit.getPlayer(UUID.fromString(policeList.get(i))) != null) {
			onlinePolice.add(policeList.get(i));
			//cycle through online police

		}
		i++;
	}
	return onlinePolice;
}
	
//check if a player has item in hand
public boolean testForItem(Player p, Material item, String DisplayName) {
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
	if (this.alreadyPolice(uuid)) {
		Main.getInstance().Data.set(uuid, false);
		Main.getInstance().SaveDataFile();;
		return;
	} else {
		return;
	}
}

public boolean isLocationSafe (Location loc1) {
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

public Location policeTp (Player player, int MaxValTp) {
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

public ArrayList<String> listPolice() {
	
	Map<String, Object> police = Main.getInstance().Data.getValues(false);

	ArrayList<String> policeList = new ArrayList<String>();
	
	
	Object[] objectPolicearray = new Object[police.keySet().toArray().length];
	objectPolicearray = police.keySet().toArray();
	
	String[] policeArrayNotFinal = new String[objectPolicearray.length];
	System.arraycopy(objectPolicearray, 0, policeArrayNotFinal, 0, objectPolicearray.length);
	
	int i = 0;
	
	while (i < policeArrayNotFinal.length) {
		if ((boolean) police.get(policeArrayNotFinal[i])) {
			policeList.add(policeArrayNotFinal[i]);
		}
		i++;
	}
	
	return policeList;
}

public void payPoliceOnArrest(Player player) {
	if (Main.getInstance().getConfig().getBoolean("PayPoliceOnArrest")) {
		//make sure vault is installed
		if (Bukkit.getServer().getPluginManager().getPlugin("Vault")!= null) {
			Economy economy = this.setupEconomy();
			economy.depositPlayer(player, 500);
			player.sendMessage(ChatColor.DARK_AQUA+"You have arrested a player and earned $500!");
		}
	}
}


private Economy setupEconomy() {
    RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
    if (rsp == null) {
        
    }
    Economy econ = rsp.getProvider();
    return econ;
}

public void takeMoneyOnArrest(Player player) {
	if (Main.getInstance().getConfig().getBoolean("TakeMoneyOnArrest")) {
		//make sure vault is installed
		if (Bukkit.getServer().getPluginManager().getPlugin("Vault")!= null) {
			
			double percent = 0;
			
			percent = Main.getInstance().getConfig().getDouble("PercentOfMoneyToTake");
			
		
			
			Economy economy = this.setupEconomy();
			
			double moneyLost = economy.getBalance(player)*(percent/100);
			
			player.sendMessage(ChatColor.DARK_AQUA+"The police have arested you and you have lost $"+moneyLost);
			
			economy.withdrawPlayer(player, moneyLost);
			
		}
	}
}


protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
    final ItemStack item = new ItemStack(material, 1);
    final ItemMeta meta = item.getItemMeta();

    // Set the name of the item
    meta.setDisplayName(name);

    // Set the lore of the item
    meta.setLore(Arrays.asList(lore));

    item.setItemMeta(meta);

    return item;
}

public boolean inSafeArea (Player police) {
	if (Main.getInstance().getConfig().getBoolean("SafeArea")) {
		//make sure worldguard is installed
		if (Bukkit.getServer().getPluginManager().getPlugin("WorldGuard")!= null) {
			
			
				//get region "block"
			RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
			//create block
			RegionQuery query = container.createQuery();
			//gets the list of all the region(s) the player is standing in
			ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(police.getLocation()));
			//create Worker
			
			for (ProtectedRegion region : set) {
				if (Main.getInstance().getConfig().getList("SafeAreas").contains(region.getId())) {
					return true;
				}
			}

			return false;
				    
			
			
			
		}
		System.out.println("You have the safe area setting in simple police toggled, but you do not have world guard installed!");
		return false;
	}
	return false;
}
/*
public void AddMissingItemsToConfig() {
	Main.getInstance().getConfig().getBoolean("PayPoliceOnArrest", true);
	Main.getInstance().getConfig().getInt("MaxPoliceTp", 50);
	Main.getInstance().saveConfig();
	
	
	
}*/

}
