package com.voidcitymc.plugins.SimplePolice;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;

import java.util.ArrayList;

public class TabComplete implements Listener {

    SPPlugin plugin;

    public TabComplete(SPPlugin plugin) {
        this.plugin = plugin;
    }

    //event handler
    @EventHandler
    public void onTab(TabCompleteEvent event) {
        if (event.getSender() instanceof Player && event.getBuffer().split(" ")[0].equalsIgnoreCase("/police")) {
            String[] buffer = event.getBuffer().split(" ");

            ArrayList<String> completions = new ArrayList<>();
            Player player = (Player) event.getSender();
            Worker work = new Worker(plugin);

            boolean isPolice = work.alreadyPolice(player.getUniqueId().toString());

            if (player.hasPermission("police.admin")) {
                //admin reload, /admin setjail, etc.
                completions = work.cmdCompletePlayer(completions, "police admin reload", buffer, false);
                completions = work.cmdCompletePlayer(completions, "police admin add", buffer, false);
                completions = work.cmdCompletePlayer(completions, "police admin remove", buffer, false);
                completions = work.cmdCompletePlayer(completions, "police admin setjail", buffer, false);
                completions = work.cmdCompletePlayer(completions, "police admin jail", buffer, true);
            }


            if ((player.hasPermission("police.help") || isPolice)) {
                completions = work.cmdCompletePlayer(completions, "police help", buffer, false);
            }
            if (player.hasPermission("police.tp") || isPolice) {
                completions = work.cmdCompletePlayer(completions, "police tp", buffer, true);
            }
            if (player.hasPermission("police.remove")) {
                completions = work.cmdCompletePlayer(completions, "police remove", buffer, true);
            }
            if (player.hasPermission("police.add")) {
                completions = work.cmdCompletePlayer(completions, "police add", buffer, true);
            }
            if (player.hasPermission("police.unjail") || isPolice) {
                completions = work.cmdCompletePlayer(completions, "police unjail", buffer, true);
            }
            if (player.hasPermission("police.chat") || isPolice) {
                completions = work.cmdCompletePlayer(completions, "police chat on", buffer, false);
                completions = work.cmdCompletePlayer(completions, "police chat off", buffer, false);
            }

            event.setCompletions(completions);

        }
    }

    //end event handler

}
