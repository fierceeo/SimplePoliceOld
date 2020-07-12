package com.voidcitymc.api.SimplePolice;

import java.util.ArrayList;
import org.bukkit.Location;
import org.bukkit.entity.Player;

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



}
