package com.voidcitymc.plugins.SimplePolice;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;


//to-do begin work

public class CrimRecordListiner implements Listener {

    //Event handlers
    @EventHandler
    public static void onTp(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof  Player && event.getEntity() instanceof Player) {
            Player hurtPlayer = (Player) event.getEntity();
            Player attacker = (Player) event.getDamager();
        }

    }

    @EventHandler
    public static void onDeath(PlayerDeathEvent event) {

    }


    //end event handlers


}
