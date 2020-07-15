package com.voidcitymc.plugins.SimplePolice;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

    public static final int DEFAULT_COOLDOWN = SPPlugin.getInstance().getConfig().getInt("FriskCooldown"); //10 minutes default
    private final Map<String, Long> cooldowns = new HashMap<>();

    public void setCooldown(UUID police, UUID player, long time) {
        if (time < 1) {
            cooldowns.remove(police.toString() + player.toString());
        } else {
            cooldowns.put(police.toString() + player.toString(), time);
        }
    }

    public long getCooldown(UUID police, UUID player) {
        long l = 0;
        return cooldowns.getOrDefault(police.toString() + player.toString(), l);
    }
}
