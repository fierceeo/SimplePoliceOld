package com.voidcitymc.plugins.SimplePolice;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PoliceListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        Worker work = new Worker();
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            //check if the player who punched someone is a police and has the police bataan
            Player damagerP = (Player) event.getDamager();
            Player damageeP = (Player) event.getEntity();
            //damager is the police
            //damagee is the criminal
            if (!work.inSafeArea(damageeP)) {
                if (work.alreadyPolice(damagerP.getUniqueId().toString()) && work.testForItem(damagerP, work.getBatonMaterial(), "Police")) /*put stuff here too) */ {
                    damageeP.sendMessage(Messages.getMessage("ArrestMsg"));
                    damageeP.teleport(Jail.jailLocation());

                    work.payPoliceOnArrest(damagerP);
                    work.takeMoneyOnArrest(damageeP);
                    SPPlugin.lastArrest.put(damagerP.getName(), damageeP.getUniqueId().toString() + "," + damageeP.getName());
                    GUI gui = new GUI();
                    gui.openInventory(damagerP);
                    event.setCancelled(true);
                }
            } else {
                damagerP.sendMessage(Messages.getMessage("ArrestSafeArea"));
            }

        }
    }


}
