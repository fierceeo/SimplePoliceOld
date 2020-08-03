package com.voidcitymc.plugins.SimplePolice;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Jail implements Listener {

    private final Map<String, Double> originaljailTime = new HashMap<>();
    private final Map<String, Long> cooldowns = new HashMap<>();
    private final Map<String, Location> previousLoc = new HashMap<>();
    
    //uuid > taskId
    private final Map<String, Integer> scheduledUnjails= new HashMap<>();

    private void setCooldown(UUID player, long time) {
        if (time < 1) {
            cooldowns.remove(player.toString());
        } else {
            cooldowns.put(player.toString(), time);
        }
    }

    private long getCooldown(UUID player) {
        long l = 0;
        return cooldowns.getOrDefault(player.toString(), l);
    }
    private boolean isJailTimeOver(UUID player) {
        return TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - this.getCooldown(player)) >= this.originaljailTime.get(player.toString());
    }


    //Event handlers
    @EventHandler
    public void onTp(PlayerTeleportEvent event) {
        if (this.isPlayerJailed(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
            int timeLeft = (int) Math.round(timeLeft(event.getPlayer().getUniqueId()));
            String timeLeftText;

            if (timeLeft == 1) {
                timeLeftText = timeLeft+Messages.getMessage("JailTimeUnitForTimeLeftEqual1");
            } else if (timeLeft < 60) {
                timeLeftText = timeLeft+Messages.getMessage("JailTimeUnitForTimeLeftUnder60");
            } else if (timeLeft == 60) {
                timeLeftText = timeLeft+Messages.getMessage("JailTimeUnitForTimeLeftEqual60");
            } else {
                timeLeftText = timeLeft+Messages.getMessage("JailTimeUnitForTimeLeftOver60");
            }


            event.getPlayer().sendMessage(Messages.getMessage("PlayerEscapeOutOfJail", timeLeftText));
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (this.isPlayerJailed(event.getEntity().getUniqueId())) {

            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(SPPlugin.getInstance(), () -> {
                Player player = event.getEntity();
                int timeLeft = (int) Math.round(timeLeft(player.getUniqueId()));
                String timeLeftText;

                if (timeLeft == 1) {
                    timeLeftText = timeLeft+Messages.getMessage("JailTimeUnitForTimeLeftEqual1");
                } else if (timeLeft < 60) {
                    timeLeftText = timeLeft+Messages.getMessage("JailTimeUnitForTimeLeftUnder60");
                } else if (timeLeft == 60) {
                    timeLeftText = timeLeft+Messages.getMessage("JailTimeUnitForTimeLeftEqual60");
                } else {
                    timeLeftText = timeLeft+Messages.getMessage("JailTimeUnitForTimeLeftOver60");
                }

                player.teleport(this.previousLoc.get(player.getUniqueId().toString()));
                player.sendMessage(Messages.getMessage("PlayerEscapeOutOfJail", timeLeftText));
            }, 1);

        }

    }


    //end event handlers

    public double getInitialJailTime(UUID player) {
        double d = 0.0;
        return originaljailTime.getOrDefault(player.toString(), d);
    }

    public double timeLeft(UUID player){
        if (this.isPlayerJailed(player)) {
            return this.originaljailTime.get(player.toString()) - TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - this.getCooldown(player));
        } else {
            return 0.0;
        }
    }

    public boolean isPlayerJailed(UUID player) {
        if (!this.cooldowns.containsKey(player.toString()) && !this.originaljailTime.containsKey(player.toString())) {
            return false;
        } else if (this.isJailTimeOver(player)) {
            this.cooldowns.remove(player.toString());
            this.originaljailTime.remove(player.toString());
            return false;
        } else {
            return true;
        }
    }

    public void jailPlayer(UUID player, Double jailTime) {
        if (Bukkit.getPlayer(player) != null) {
            this.previousLoc.put(player.toString(), Bukkit.getPlayer(player).getLocation());
            Bukkit.getPlayer(player).teleport(this.jailLocation());
        }
        this.setCooldown(player, System.currentTimeMillis());
        this.originaljailTime.put(player.toString(), jailTime);

        
        if (this.originaljailTime.containsKey(player.toString())) {
        	Bukkit.getScheduler().cancelTask(this.scheduledUnjails.get(player.toString()));
        }
        int id = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(SPPlugin.getInstance(), () -> {
            this.unjailPlayer(player, true);
            if (Bukkit.getPlayer(player) != null) {
                Bukkit.getPlayer(player).sendMessage(Messages.getMessage("JailRelease"));
            }
        }, (long) (jailTime*20));
        
        this.scheduledUnjails.put(player.toString(), id);

    }

    public void unjailPlayer(UUID player, boolean teleportBack) {
        this.cooldowns.remove(player.toString());
        this.originaljailTime.remove(player.toString());
        this.scheduledUnjails.remove(player.toString());
        if (teleportBack && this.previousLoc.containsKey(player.toString())) {
            if (Bukkit.getPlayer(player) != null) {
                Bukkit.getPlayer(player).teleport(this.previousLoc.get(player.toString()));
            }
            this.previousLoc.remove(player.toString());
        }
    }
    
    public Location jailLocation() {
    	@SuppressWarnings("unchecked")
		HashMap<String, Object> jailLoc = (HashMap<String, Object>) SPPlugin.getInstance().getConfig().getMapList("JailLocation");
    	return new Location(Bukkit.getWorld((String) jailLoc.get("World")), (int) jailLoc.get("X"), (int) jailLoc.get("Y"), (int) jailLoc.get("Z"));
    }

}
