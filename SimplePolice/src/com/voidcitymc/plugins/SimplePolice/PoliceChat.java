package com.voidcitymc.plugins.SimplePolice;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class PoliceChat implements Listener {

    SPPlugin plugin;

    public PoliceChat(SPPlugin plugin) {
        this.plugin = plugin;
    }

    private static final ArrayList<String> playersWithToggledChat = new ArrayList<>();

    //Event handlers
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (isPoliceChatToggledOn(event.getPlayer().getUniqueId().toString())) {
            event.setCancelled(true);
            event.getRecipients().clear();
            int i = 0;
            String playerName;
            if (plugin.getConfig().getBoolean("UsePlayerDisplayNameInPoliceChat")) {
                //use display name
                playerName = event.getPlayer().getDisplayName();
            } else {
                //use standard name
                playerName = event.getPlayer().getName();
            }

            while (i <  getPlayersWithToggledChat().size()) {
                if (Bukkit.getPlayer(UUID.fromString(getPlayersWithToggledChat().get(i))) != null) {
                    Objects.requireNonNull(Bukkit.getPlayer(UUID.fromString(getPlayersWithToggledChat().get(i)))).sendMessage(Messages.getMessage("PoliceChat", playerName, event.getMessage()));
                }
                i++;
            }
        }
    }

    public void onLeave(PlayerQuitEvent event) {
        getPlayersWithToggledChat().remove(event.getPlayer().getUniqueId().toString());
    }

    //end Event handlers

    public static void addPlayerToToggledChat(String uuid) {
        if (!playersWithToggledChat.contains(uuid)) {
            playersWithToggledChat.add(uuid);
        }
    }

    public static void removePlayerFromToggledChat(String uuid) {
        playersWithToggledChat.remove(uuid);
    }

    public static boolean isPoliceChatToggledOn(String uuid) {
        return playersWithToggledChat.contains(uuid);
    }

    public static void toggleChat(String uuid) {
        if (isPoliceChatToggledOn(uuid)) {
            removePlayerFromToggledChat(uuid);
        } else {
            addPlayerToToggledChat(uuid);
        }
    }

    public static ArrayList<String> getPlayersWithToggledChat() {
        return playersWithToggledChat;
    }

}
