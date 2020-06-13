package com.voidcitymc.plugins.SimplePolice;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PoliceListener implements Listener {
	
    @EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		worker work = new worker();
		if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
			//check if the player who punched someone is a police and has the police bitan
			Entity damagerE = (Entity) event.getDamager();
    			Player damagerP = (Player) damagerE;
    			Entity damageeE = (Entity) event.getEntity();
    			Player damageeP = (Player) damageeE;
    			//damager is the police
    			//damagee is the criminal
			if (!work.inSafeArea(damageeP)) {
    				if (work.alreadyPolice(damagerP.getUniqueId().toString()) && worker.TestForItem(damagerP, Material.BLAZE_ROD, "Police")) /*put stuff here too) */ {
    					System.out.println("A player has been arrested");
    					damageeP.sendMessage("You have been arrested");
    					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "essentials:jail "+damageeP.getName()+" jail1 4m");
    					worker.PayPoliceOnArrest(damagerP);
    					worker.TakeMoneyOnArrest(damageeP);
    					Main.lastArrest.put(damagerP.getName(), damageeP.getName());
    					GUI gui = new GUI();
    					gui.openInventory(damagerP);
    				}
			} else {
				damagerP.sendMessage(ChatColor.DARK_AQUA+"You can't arrest that player, because they are in a safe area");
			}
    		
    		
    		
    		
    		}
	}
	
	
}
