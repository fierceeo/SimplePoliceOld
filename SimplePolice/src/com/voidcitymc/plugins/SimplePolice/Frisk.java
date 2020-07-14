package com.voidcitymc.plugins.SimplePolice;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.ChatColor;

public class Frisk implements Listener {
	
    @EventHandler
	public void onFrisk(PlayerInteractEntityEvent event) {
    	if (event.getRightClicked() instanceof Player) {
        	Worker work = new Worker();
        	if (work.friskingEnabled() && work.alreadyPolice(event.getPlayer().getUniqueId().toString()) && event.getPlayer().getInventory().getItemInMainHand().getType().equals(work.getFriskStickMaterial())) {
        		//begin frisk
        		Player suspectedPlayer = (Player) event.getRightClicked();
        		suspectedPlayer.sendMessage(ChatColor.DARK_AQUA+"You are being frisked");
        		PlayerInventory invToScan = suspectedPlayer.getInventory();
        		
        		ItemStack[] contents = invToScan.getContents();
        		int i = 0;
        		
        		List<ItemStack> controband = work.getFriskList();
        		
        		ArrayList<Material> controbandMats = new ArrayList<Material>();
        		
        		while (i < controband.size()) {
        			controbandMats.add(controband.get(i).getType());
        			i++;
        		}
        		i = 0;
        		
        		ArrayList<String> textToReturn = new ArrayList<String>();
        		textToReturn.add(ChatColor.DARK_AQUA+"Controband items found in player");
        		//Boolean guilty = false;
        		//Double priceToPay = 0.0;
        		
        		while (i < contents.length) {
        			if (controbandMats.contains(contents[i].getType())) {
        				if (SPPlugin.getInstance().Controband.getInt("PrecentOfFindingControband")-Math.random()*100 >= 0) {
            				textToReturn.add(ChatColor.DARK_AQUA+""+contents[i].getAmount()+"x "+contents[i].getType().toString());
            		        //guilty = true;
            		        event.getPlayer().getInventory().addItem(contents[i]);
            		        suspectedPlayer.getInventory().setItem(i,null);


        				}

        			}
        			i++;
        		}
        		
                if (textToReturn.size() < 2) {
                	textToReturn.add(ChatColor.DARK_AQUA+"Nothing");
                }
                
                event.getPlayer().sendMessage((String[])textToReturn.toArray());
        		
        	}
        	
    	}

	}
	
	
}
