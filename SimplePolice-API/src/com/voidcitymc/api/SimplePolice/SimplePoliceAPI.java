package com.voidcitymc.api.SimplePolice;

import java.util.ArrayList;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface SimplePoliceAPI {
	
	
ArrayList<String> onlinePoliceList();
	/**
	 * List of all the online police
	 * 
	 * @return ArrayList<String> An ArrayList of all the online police user names.
	 */

	
void addPolice (String uuid);
	/**
	 * Adds a police
	 * 
	 * @pram uuid The uuid (in string format) of the player to add
	 */


boolean isPolice (String uuid);
	/**
	 * Checks if a player is a police
	 * 
	 * @pram uuid The uuid (in string format) of the player to check
	 * 
	 * @return boolean Returns if the player is a police
	 */


void removePolice(String uuid);
    /*
     * Remove a police from the list
     * 
     * @pram uuid The uuid (in string format) of the player to remove
     */

Location policeTp (Player player, int farthestTpDistance);
     /*
      * Police teleport
      * 
      * @pram player The player to teleport
      * @pram farthestTpDistance The farthest distance to teleport a player from another player
      * 
      * @return Location The random location given to teleport to
      */

Location policeTp (Player player);
     /*
      * Police teleport a player
      * 
      * @pram player The player to teleport
      * 
      * @return Location The random location given to teleport to
      */

ArrayList<String> listPolice();
     /*
      * List of all the police (in string uuid format)
      * 
      * @return ArrayList<String> List of all the police (in string uuid format)
      */


boolean inSafeArea (Player player);
     /*
      * Check if a player is in a safe area
      * 
      * @pram player Player to check
      * 
      * @return boolean if the player is in a safe area
      */


}