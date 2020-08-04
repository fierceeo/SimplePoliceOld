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

    private static final Map<String, Double> originaljailTime = new HashMap<>();
    private static final Map<String, Long> cooldowns = new HashMap<>();
    private static final Map<String, Location> previousLoc = new HashMap<>();
    
    //uuid > taskId
    private static final Map<String, Integer> scheduledUnjails= new HashMap<>();

    private static void setCooldown(UUID player, long time) {
        if (time < 1) {
            cooldowns.remove(player.toString());
        } else {
            cooldowns.put(player.toString(), time);
        }
    }

    private static long getCooldown(UUID player) {
        long l = 0;
        return cooldowns.getOrDefault(player.toString(), l);
    }
    private static boolean isJailTimeOver(UUID player) {
        return TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - getCooldown(player)) >= originaljailTime.get(player.toString());
    }

    //Event handlers
    @EventHandler
    public static void onTp(PlayerTeleportEvent event) {
        if (isPlayerJailed(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Messages.getMessage("PlayerEscapeOutOfJail", timeLeftText(event.getPlayer().getUniqueId())));
        }
    }

    @EventHandler
    public static void onDeath(PlayerDeathEvent event) {
        if (isPlayerJailed(event.getEntity().getUniqueId())) {

            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(SPPlugin.getInstance(), () -> {
                Player player = event.getEntity();

                player.teleport(previousLoc.get(player.getUniqueId().toString()));
                player.sendMessage(Messages.getMessage("PlayerEscapeOutOfJail", timeLeftText(player.getUniqueId())));
            }, 1);

        }

    }


    //end event handlers

    public static String timeLeftText(UUID player) {
        int timeLeft = ((int) Math.round(timeLeft(player)));
        String timeLeftText;

        if (timeLeft == 1) {
            timeLeftText = 1+" "+Messages.getMessage("JailTimeUnitForTimeLeftEqual1");
        } else if (timeLeft < 60) {
            timeLeftText = timeLeft+" "+Messages.getMessage("JailTimeUnitForTimeLeftUnder60");
        } else if (timeLeft == 60) {
            timeLeftText = 1+" "+Messages.getMessage("JailTimeUnitForTimeLeftEqual60");
        } else {
            timeLeftText = Math.round(((double)timeLeft)/60)+" "+Messages.getMessage("JailTimeUnitForTimeLeftOver60");
        }
        return timeLeftText;
    }

    public static String timeLeftText(int seconds) {
        String timeLeftText;

        if (seconds == 1) {
            timeLeftText = 1+" "+Messages.getMessage("JailTimeUnitForTimeLeftEqual1");
        } else if (seconds < 60) {
            timeLeftText = seconds +" "+Messages.getMessage("JailTimeUnitForTimeLeftUnder60");
        } else if (seconds == 60) {
            timeLeftText = 1+" "+Messages.getMessage("JailTimeUnitForTimeLeftEqual60");
        } else {
            timeLeftText = Math.round(((double) seconds)/60)+" "+Messages.getMessage("JailTimeUnitForTimeLeftOver60");
        }
        return timeLeftText;
    }

    public static double getInitialJailTime(UUID player) {
        double d = 0.0;
        return originaljailTime.getOrDefault(player.toString(), d);
    }

    public static double timeLeft(UUID player){
        if (isPlayerJailed(player)) {
            return originaljailTime.get(player.toString()) - TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - getCooldown(player));
        } else {
            return 0.0;
        }
    }

    public static boolean isPlayerJailed(UUID player) {
        if (!cooldowns.containsKey(player.toString()) && !originaljailTime.containsKey(player.toString())) {
            return false;
        } else if (isJailTimeOver(player)) {
            cooldowns.remove(player.toString());
            originaljailTime.remove(player.toString());
            return false;
        } else {
            return true;
        }
    }

    public static void jailPlayer(UUID player, Double jailTime) {
        if (Bukkit.getPlayer(player) != null) {
            previousLoc.put(player.toString(), Bukkit.getPlayer(player).getLocation());
            Bukkit.getPlayer(player).teleport(jailLocation());
        }
        setCooldown(player, System.currentTimeMillis());
        originaljailTime.put(player.toString(), jailTime);

        
        if (scheduledUnjails.containsKey(player.toString())) {
        	Bukkit.getScheduler().cancelTask(scheduledUnjails.get(player.toString()));
        }
        int id = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(SPPlugin.getInstance(), () -> {
            unjailPlayer(player, true);
            if (Bukkit.getPlayer(player) != null) {
                Bukkit.getPlayer(player).sendMessage(Messages.getMessage("JailRelease"));
            }
        }, (long) (jailTime*20));
        
        scheduledUnjails.put(player.toString(), id);

    }

    public static void unjailPlayer(UUID player, boolean teleportBack) {
        cooldowns.remove(player.toString());
        originaljailTime.remove(player.toString());
        scheduledUnjails.remove(player.toString());
        if (teleportBack && previousLoc.containsKey(player.toString())) {
            if (Bukkit.getPlayer(player) != null) {
                Bukkit.getPlayer(player).teleport(previousLoc.get(player.toString()));
            }
            previousLoc.remove(player.toString());
        }
    }
    
    public static Location jailLocation() {
    	@SuppressWarnings("unchecked")
		HashMap<String, Object> jailLoc = ((HashMap<String,Object>) SPPlugin.getInstance().getConfig().getList("JailLocation").get(0));
    	return new Location(Bukkit.getWorld((String) jailLoc.get("World")), (int) jailLoc.get("X"), (int) jailLoc.get("Y"), (int) jailLoc.get("Z"));
    }

}
