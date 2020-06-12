package com.voidcitymc.plugins.SimplePolice;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerTeleport implements Listener {
	
    @EventHandler (priority = EventPriority.HIGHEST)
	public void onTp(PlayerTeleportEvent event) {
		final boolean isArrest = Main.isArrestedForTp.get(event.getPlayer().getName());
    	if (isArrest) {
    		event.setCancelled(false);
    	}
    	
	}
	
	
}
