package com.voidcitymc.www;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
//import org.bukkit.event.entity.EntityDamageEvent;
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
    		if (work.alreadyPolice(damagerP.getUniqueId().toString()) && worker.TestForItem(damagerP, Material.BLAZE_ROD, "Police")) /*put stuff here too) */ {
    			System.out.println("A player has been arrested");
    			damagerP.sendMessage("You have arrested "+ damageeP.getName());
    			damageeP.sendMessage("You have been arrested");
    			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "essentials:jail "+damageeP.getName()+" jail1");
    		}
    	
    		
    		
    		
    		
    	}
	}
	
	
}
