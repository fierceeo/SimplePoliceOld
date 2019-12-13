package com.voidcitymc.www;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class MyListener implements Listener {
	
    @EventHandler
	public void onDamage(Entity damager, Entity damagee, EntityDamageEvent.DamageCause cause, double damage) {
    	worker work = new worker();
    	if (damager instanceof player && damagee instanceof player) {
    		//check if the player who punched someone is a police and has the police bitan
    		if (work.allreadyPolice(work.playerToUUID(work.playerToString(damager))) &&  ) {
    			
    		}
    	
    		
    		
    		
    		
    	}
	}
	
	
}
