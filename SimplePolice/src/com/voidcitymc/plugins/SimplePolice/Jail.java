package com.voidcitymc.plugins.SimplePolice;

import com.voidcitymc.api.SimplePolice.SimplePolice;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import scala.concurrent.impl.FutureConvertersImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Jail implements Listener {

    private final Map<String, Double> originaljailTime = new HashMap<>();
    private final Map<String, Long> cooldowns = new HashMap<>();

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


                player.sendMessage(Messages.getMessage("PlayerEscapeOutOfJail", timeLeftText));
            }, 1);

        }

    }


    //end event handlers

    public double getInitialJailTime(UUID player) {
        double l = 0;
        return originaljailTime.getOrDefault(player.toString(), l);
    }

    public double timeLeft(UUID player){
        return this.originaljailTime.get(player.toString()) - TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - this.getCooldown(player));
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
        this.setCooldown(player, System.currentTimeMillis());
        this.originaljailTime.put(player.toString(), jailTime);
    }

    public void unjailPlayer(UUID player) {
        this.cooldowns.remove(player.toString());
        this.originaljailTime.remove(player.toString());
    }

}
