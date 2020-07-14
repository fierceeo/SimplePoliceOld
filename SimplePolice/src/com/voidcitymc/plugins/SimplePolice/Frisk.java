package com.voidcitymc.plugins.SimplePolice;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.ChatColor;

public class Frisk implements Listener {
	private final CooldownManager cooldownManager = new CooldownManager();
    @EventHandler
	public void onFrisk(PlayerInteractEntityEvent event) {
    	if (event.getRightClicked() instanceof Player) {
        	Worker work = new Worker();
        	if (work.friskingEnabled() && work.alreadyPolice(event.getPlayer().getUniqueId().toString()) && event.getPlayer().getInventory().getItemInMainHand().getType().equals(work.getFriskStickMaterial())) {
        		//begin frisk
        		Player suspectedPlayer = (Player) event.getRightClicked();
        		
    			long timeLeft = System.currentTimeMillis() - cooldownManager.getCooldown(event.getPlayer().getUniqueId(), suspectedPlayer.getUniqueId());
                
                if (TimeUnit.MILLISECONDS.toSeconds(timeLeft) >= CooldownManager.DEFAULT_COOLDOWN){
                    cooldownManager.setCooldown(event.getPlayer().getUniqueId(), suspectedPlayer.getUniqueId(), System.currentTimeMillis());


            		
            		
            		
            		
            		
            		
            		suspectedPlayer.sendMessage(ChatColor.DARK_AQUA+"You are being frisked");
            		event.getPlayer().sendMessage(ChatColor.DARK_AQUA+"You are now frisking "+suspectedPlayer.getName());
            		PlayerInventory invToScan = suspectedPlayer.getInventory();
            		
            		ItemStack[] contents = invToScan.getContents();
            		int i = 0;
            		
   
            		
            		while (i < contents.length) {
            			contents[i].setAmount(1);
            			i++;
            		}
            		i = 0;
            		
            		ArrayList<String> textToReturn = new ArrayList<String>();
            		Boolean guilty = false;
            		//Double priceToPay = 0.0;
            		
            		while (i < contents.length) {
            			if (work.isItemControband(contents[i])) {
            				if (SPPlugin.getInstance().Controband.getInt("PrecentOfFindingControband")-Math.random()*100 >= 0) {
                				textToReturn.add(ChatColor.DARK_AQUA+""+invToScan.getContents()[i].getAmount()+"x "+invToScan.getContents()[i].getItemMeta().getDisplayName());
                		        guilty = true;
                		        event.getPlayer().getInventory().addItem(invToScan.getContents()[i]);
                		        suspectedPlayer.getInventory().setItem(i,null);


            				}

            			}
            			i++;
            		}
            		
            		if (guilty) {
            			suspectedPlayer.sendMessage(ChatColor.DARK_AQUA+"The police have found illigeal items in your inventory and have taken them, here are the items you have lost:");
            			suspectedPlayer.sendMessage((String[])textToReturn.toArray());
            		} else {
            			suspectedPlayer.sendMessage(ChatColor.DARK_AQUA+"The police have scanned your inventory and have found no illigeal items");
            		}
            		
            		event.getPlayer().sendMessage(ChatColor.DARK_AQUA+"Contraband items found in player:");
                    if (textToReturn.size() < 1) {
                    	textToReturn.add(ChatColor.DARK_AQUA+"Nothing");
                    }
                    
                    event.getPlayer().sendMessage((String[])textToReturn.toArray());
            		
                } else {
                    event.getPlayer().sendMessage(ChatColor.RED.toString() + "You can frisk this player again in "+ (CooldownManager.DEFAULT_COOLDOWN - TimeUnit.MILLISECONDS.toSeconds(timeLeft)) + " seconds");
                }
                
        	}
        	
    	}

	}
	
	
}
