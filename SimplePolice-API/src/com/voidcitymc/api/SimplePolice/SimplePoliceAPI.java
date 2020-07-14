package com.voidcitymc.api.SimplePolice;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public interface SimplePoliceAPI {
	
/**
 * List of all the online police
 * 
 * @return ArrayList<String> An ArrayList of all the online police user names.
 */
ArrayList<String> onlinePoliceList();


/**
 * Adds a police
 * 
 * @param uuid The uuid (in string format) of the player to add
 */

void addPolice (String uuid);

/**
 * Checks if a player is a police
 * 
 * @param uuid The uuid (in string format) of the player to check
 * 
 * @return boolean Returns if the player is a police
 */

boolean isPolice (String uuid);
/**
 * Remove a police from the list
 * 
 * @param uuid The uuid (in string format) of the player to remove
 */
void removePolice(String uuid);

/**
 * Police teleport
 * 
 * @param player The player to teleport
 * @param farthestTpDistance The farthest distance to teleport a player from another player
 * 
 * @return Location The random location given to teleport to
 */
Location policeTp (Player player, int farthestTpDistance);

/**
 * Police teleport a player
 * 
 * @param player The player to teleport
 * 
 * @return Location The random location given to teleport to
 */
Location policeTp (Player player);

/**
 * List of all the police (in string uuid format)
 * 
 * @return ArrayList<String> List of all the police (in string uuid format)
 */
ArrayList<String> listPolice();


/**
 * Check if a player is in a safe area
 * 
 * @param player Player to check
 * 
 * @return boolean if the player is in a safe area
 */
boolean inSafeArea (Player player);

/**
 * Returns the material of the baton (set in the config)
 * 
 * @return Material material of baton
 */
Material getBatonMaterial();


/**
 * Returns the material of the frisk stick (set in the config)
 * 
 * @return Material material of frisk stick
 */
public Material getFriskStickMaterial();

/**
 * Add an item to the controband items list
 * 
 * @param item The item to add
 */
public void addToFriskList(ItemStack item);

/**
 * Remove an item to the controband items list
 * 
 * @param item The item to remove
 */
public void removeFromFriskList(ItemStack item);

/**
 * Return a list of all the controband items
 * 
 * @return List<ItemStack> A list of all the controband items
 */
public List<ItemStack> getFriskList();

/**
 * Return if frisking is enabled
 * 
 * @return boolean If frisking is enabled
 */
public boolean friskingEnabled();


}
