package com.voidcitymc.api.SimplePolice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
Material getFriskStickMaterial();

/**
 * Add an item to the contraband items list
 * 
 * @param item The item to add
 */
void addToFriskList(ItemStack item);

/**
 * Remove an item to the contraband items list
 * 
 * @param item The item to remove
 */
void removeFromFriskList(ItemStack item);

/**
 * Return a list of all the contraband items
 * Not a recommended way to check if an item is contraband, because it could be a gun.
 * 
 * @return List<ItemStack> A list of all the contraband items
 */
List<ItemStack> getFriskList();

/**
 * Return if frisking is enabled
 * 
 * @return boolean If frisking is enabled
 */
boolean friskingEnabled();

/**
 * Check if an item is contraband (recommended way)
 *
 * @param item item to check
 *
 * @return boolean if the item is contraband
 */
boolean isItemContraband(ItemStack item);

/**
 * Get a plugin message with the path specified in messages.yml
 *
 * @param path the path of the message
 *
 * @param variables the variables to fill in if this is left blank, then they will be represented as $1, $2, etc.
 *
 * @return String the message with color codes translated
 *
 */
String getMessage(String path, String... variables);

/**
 * Returns a map of all the messages in path > message form
 * Variables are represented as $1, $2, $3, etc.
 *
 * @return Map<String, String>
 */
Map<String, String> getMessagesMap();

}
